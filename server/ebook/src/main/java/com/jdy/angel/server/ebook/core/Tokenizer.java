package com.jdy.angel.server.ebook.core;

import com.jdy.angel.server.ebook.core.labels.Label;

import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Aglet
 * @create 2022/7/7 14:43
 */
class Tokenizer implements Consumer<String>, Supplier<Node> {

    /**
     * 读取字符串
     */
    private final StringBuilder builder = new StringBuilder();

    private boolean textual;

    private boolean strict;

    /**
     * 计数器
     */
    private int row = 0;

    private final Stack<Node> stack = new Stack<>();

    @Override
    public void accept(String item) {
        for (int i = 0, size = item.length(); i < size; i++) {
            if (builder.isEmpty()) {
                var index = item.indexOf(Constant.PREFIX, i);
                if (index < 0) {
                    // ..........
                    if (item.contains(Constant.SUFFIX)) {
                        if (strict) {
                            throw new IllegalArgumentException(row + "  " + item);
                        }
                    }
                    textual = true;
                    builder.append(item);
                    return;
                }
                // ....... <
                var sub = item.substring(i, index++).strip();
                var to = next0(item, index, sub);
                if (to < 0) {
                    return;
                }
                i = to;
            } else if (textual) {
                var index = item.indexOf(Constant.PREFIX);
                if (index < 0) {
                    // <> .......
                    builder.append(item);
                    return;
                }
                // .....<...
                var sub = builder + item.substring(i, index++).strip();
                builder.setLength(0);
                var to = next0(item, index, sub);
                if (to < 0) {
                    return;
                }
                i = to;
            } else {
                var index = item.indexOf(Constant.SUFFIX);
                if (index < 0) {
                    // < .....
                    builder.append(item);
                    return;
                }
                // < ....>
                var sub = builder + item.substring(i, index++).strip();
                builder.setLength(0);
                pop(sub);
                i = index;
            }
        }
        row++;
    }

    private int next0(String s, int index, String sub) {
        if (!stack.isEmpty()) {
            stack.peek().add(sub);
        }
        var to = s.indexOf(Constant.SUFFIX, index);
        if (to < 0) {
            // ..... < ......
            textual = false;
            builder.append(s.substring(index));
            return to;
        }
        // ...... <......> ?
        sub = s.substring(index, to);
        pop(sub);
        return to;
    }

    private void pop(String sub) {
        var label = Label.of(sub);
        if (stack.isEmpty()) {
            stack.push(label.toNode());
            return;
        }
        var peek = stack.peek();
        var type = label.getType();
        switch (type) {
            case END -> peek.add(label.toNode());
            case OPEN -> stack.push(label.toNode());
            case CLOSE -> {
                do {
                    var pop = stack.pop();
                    peek = stack.peek();
                    peek.add(pop);
                    if (pop.match(label)) {
                        break;
                    }
                } while (true);
            }
        }
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    @Override
    public Node get() {
        if (stack.isEmpty()) {
            throw new RuntimeException();
        }
        return stack.pop();
    }
}
