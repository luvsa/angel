package com.jdy.angel.server.ebook.core;

import com.jdy.angel.server.ebook.core.labels.Tuple;

import java.util.Stack;
import java.util.function.Supplier;

/**
 * @author Aglet
 * @create 2022/7/5 20:57
 */
public class Manager implements Supplier<Node> {

    private String charset;

    private final Iterable<Tuple> iterable;

    private final Stack<Node> stack;

    public Manager(String code) {
        this.iterable = null;
//                Tuple.iterable(code);
        this.stack = new Stack<>();
    }

    @Override
    public Node get() {
//        for (var item : iterable) {
//            var label = item.getLabel();
//            var text = item.getText();
//            if (label.isFinished()) {
//                while (!stack.isEmpty()) {
//                    var peek = stack.peek();
//                    var kind = peek.match(label);
//                    if (kind == Kind.Unmatched) {
//                        stack.pop();
//                        stack.peek().add(peek);
//                        continue;
//                    }
//
//                    if (kind == Kind.half) {
//                        peek.add(label.toNode());
//                        peek.add(text.toNode());
//                        if (charset == null){
//                            charset = label.get("charset");
//                        }
//                        break;
//                    }
//
//                    if (kind == Kind.matched) {
//                        var pop = stack.pop();
//                        peek = stack.peek();
//                        peek.add(pop);
//                        peek.add(text.toNode());
//                        break;
//                    }
//                }
//                continue;
//            }
//            var node = Node.of(label, text);
//            stack.push(node);
//        }
        if (stack.isEmpty()) {
            throw new RuntimeException();
        }
        return stack.pop();
    }
}
