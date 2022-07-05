package com.jdy.angel.server.ebook.core;

import java.util.*;
import java.util.function.Consumer;


/**
 * @author Aglet
 * @create 2022/7/3 21:39
 */
class Tokenizer implements Iterable<Section> {
    private final Iterator<Section> iterator;

    /**
     * code 栈
     */
    private final Stack<Segment> stack;

    private Consumer<Label> consumer;

    private int index;

    private int cursor;

    public Tokenizer(String html) {
        this.iterator = new Sections(html);
        stack = new Stack<>();
    }

    public Segment get() {
//        do {
//            var next = next();
//            if (next == END) {
//                break;
//            }
//
//            if (next == null) {
//                continue;
//            }
//
//            var block = next.create();
//            if (stack.isEmpty()) {
//                stack.push(block);
//                continue;
//            }
//
//            if (block.isFinished()) {
//                var peek = stack.peek();
//                peek.merge(block);
//                continue;
//            }

//            stack.push(block);
//        } while (true);
        return null;
    }

    @Override
    public Iterator<Section> iterator() {
        return this.iterator;
    }


//    private String getText() {
//        if (index == cursor) {
//            index++;
//            return Constant.EMPTY;
//        }
//        var sub = html.substring(index, cursor);
//        index = cursor + 1;
//        return sub;
//    }

    /**
     * 遍历器
     */
    private static class Sections implements Iterator<Section> {

        private final static String PREFIX = "<";

        private final static String SUFFIX = ">";

        private final String code;

        private int index;

        private boolean finished;

        private Sections(String code) {
            this.code = code;
        }

        @Override
        public boolean hasNext() {
            return !finished;
        }

        @Override
        public Section next() {
            if (finished) {
                throw new RuntimeException("没有其他元素");
            }

            var from = code.indexOf(PREFIX, index);
            if (from < 0) {
                throw new RuntimeException("数组越界： " + from);
            }

            // txt 文本
            var text = code.substring(index, from);
            var temp = code.indexOf(PREFIX, from + 1);
            var to = code.indexOf(SUFFIX, from);
            if (to > from) {
                finished = temp < 0;
                var sub = code.substring(from + 1, to);
                this.index = to + 1;
                if (temp > to || temp < 0) {
                    return new Section(text, sub);
                }
                //  TODO 需要避开 <script> 代码
                throw new RuntimeException("错误语法： " + sub);
            }
            throw new IllegalArgumentException();
        }
    }
}
