package com.jdy.angel.server.ebook.core;

import com.jdy.angel.server.ebook.core.labels.Label;
import com.jdy.angel.utils.FileUtil;

import java.util.Stack;

/**
 * 基于文件实现
 *
 * @author Aglet
 * @create 2022/7/6 20:48
 */
class ResourceParser implements Parser {

    /**
     * 文件路径
     */
    private final String resource;

    /**
     * 读取字符串
     */
    private final StringBuilder builder;

    private boolean textual;

    private final Stack<Node> stack;

    private int row;

    public ResourceParser(String resource) {
        this.resource = resource;
        this.builder = new StringBuilder();
        this.stack = new Stack<>();
    }

    @Override
    public Node get() {
        FileUtil.readResource(resource, this::next);
        if (stack.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return stack.pop();
    }

    private void next(String s) {
        row++;
        for (int i = 0, size = s.length(); i < size; i++) {
            if (builder.isEmpty()) {
                var index = s.indexOf(Constant.PREFIX, i);
                if (index < 0) {
                    // ..........
//                    if (s.contains(Constant.SUFFIX)) {
//                        // ...... >
//                        throw new IllegalArgumentException(row + "  " +  s);
//                    }
                    textual = true;
                    builder.append(s);
                    return;
                }
                // ....... <
                var sub = s.substring(i, index++).strip();
                var to = next0(s, index, sub);
                if (to < 0) {
                    return;
                }
                i = to;
                continue;
            }

            if (textual) {
                var index = s.indexOf(Constant.PREFIX);
                if (index < 0) {
                    // <> .......
                    builder.append(s);
                    return;
                }
                // .....<...
                var sub = builder + s.substring(i, index++).strip();
                builder.setLength(0);
                var to = next0(s, index, sub);
                if (to < 0) {
                    return;
                }
                i = to;
                continue;
            }

            var index = s.indexOf(Constant.SUFFIX);
            if (index < 0) {
                // < .....
                builder.append(s);
                return;
            }
            // < ....>
            var sub = builder + s.substring(i, index++).strip();
            builder.setLength(0);
            pop(sub);
            i = index;
        }

    }

//    private String check(String s) {
//        if (charset == null) {
//            return s;
//        }
//        var bytes = s.getBytes();
//        return new String(bytes, charset);
//    }

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
            case END -> {
                peek.add(label.toNode());
//                if (charset == null) {
//                    this.charset = Charset.forName(label.get("charset"));
//                }
            }
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
}
