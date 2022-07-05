package com.jdy.angel.server.ebook.core;

import java.util.Iterator;
import java.util.Stack;


/**
 * @author Aglet
 * @create 2022/7/3 21:39
 */
class Tokenizer implements Iterable<Section>, Iterator<Section> {

    private final String code;

    private int index;

    private boolean finished;

    /**
     * code 栈
     */
    private final Stack<Segment> stack;

    public Tokenizer(String html) {
        this.code = html;
        stack = new Stack<>();
    }

    @Override
    public Iterator<Section> iterator() {
        return this;
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

        var from = code.indexOf(Constant.PREFIX, index);
        if (from < 0) {
            throw new RuntimeException("数组越界： " + from);
        }

        // txt 文本
        var text = code.substring(index, from);
        var temp = code.indexOf(Constant.PREFIX, from + 1);
        var to = code.indexOf(Constant.SUFFIX, from);
        if (to > from) {
            finished = temp < 0;
            var sub = code.substring(from + 1, to);
            this.index = to + 1;
            var name = getName(sub.strip());

            // 1. 注释问题
            // 2. script 问题
            // 3. style 问题


            if (temp > to || temp < 0) {
                return new Section(text, sub);
            }
            //  TODO 需要避开 <script> 代码
            throw new RuntimeException("错误语法： " + sub);
        }
        throw new IllegalArgumentException();
    }

    private String getName(String txt) {
        var index = txt.indexOf(Constant.BLANK);
        if (index < 0) {
            return txt;
        }
        return txt.substring(0, index);
    }
}
