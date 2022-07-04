package com.jdy.angel.server.ebook.core;

import java.util.Stack;
import java.util.function.Consumer;


/**
 * @author Aglet
 * @create 2022/7/3 21:39
 */
class Tokenizer {
    private final String html;

    /**
     * code 栈
     */
    private final Stack<Label> stack;

    private Consumer<Label> consumer;

    private int index;

    private int cursor;

    public Tokenizer(String html) {
        this.html = html;
        stack = new Stack<>();
    }

    public Label get() {
        for (var size = html.length(); cursor < size; cursor++) {
            var c = html.charAt(cursor);
            switch (c) {
                case '<' -> startLabel();
                case '>' -> endLabel();
                case '/' -> offLabel();
                case ' ' -> guess();
                case '=' -> pushKey();
                

            }
        }

        if (stack.isEmpty()){
            return null;
        }
        return stack.pop();
    }

    private void pushKey() {
        var text = getText();
        var pop = stack.pop();
        stack.push(new Key(pop, text));
    }

    private void guess() {
        var peek = stack.peek();
        if (peek.isFinish()){
            return;
        }

        var text = getText();
        var label = peek.getLabel(text);
        if (label == peek){
            return;
        }
        var pop = stack.pop();
        label.add(pop.getChildren());
        stack.push(label);
    }

    private void offLabel() {
        // </ body>  <br />
        var pop = stack.pop();
        stack.peek().merge(pop);
        stack.push(new End());
    }

    private void endLabel() {
        var text = getText();
        var peek = stack.peek();
        if (peek.isEnd(text)) {
            popLabel();
            return;
        }
        var label = peek.getLabel(text);
        if (label == peek){
            return;
        }
        var pop = stack.pop();
        label.add(pop.getChildren());
        stack.push(label);
        label.setFinish(true);
    }

    private void popLabel() {
        var pop = stack.pop();
        var peek = stack.peek();
        if (peek.match(pop)) {
            stack.pop();
            var parent = stack.peek();
            parent.add(peek);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String getText() {
        if (index == cursor) {
            return "";
        }
        var sub = html.substring(index, cursor);
        index = cursor + 1;
        return sub;
    }

    private void startLabel() {
        var text = getText();
        stack.push(new Holder(text));
        index = cursor + 1;
    }
}
