package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Constant;

import java.util.Iterator;

/**
 * @author Aglet
 * @create 2022/7/5 18:31
 */
class Tokenizer implements Iterable<Tuple>, Iterator<Tuple> {

    private boolean finished;

    Tokenizer(String code) {
//        this.code = code;
    }

    @Override
    public Iterator<Tuple> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return !finished;
    }

    @Override
    public Tuple next() {
//        if (finished) {
//            throw new RuntimeException("没有其他元素");
//        }
//        var from = code.indexOf(Constant.PREFIX, index);
//        if (from < 0) {
//            throw new RuntimeException("数组越界： " + from);
//        }
//        var to = code.indexOf(Constant.SUFFIX, from);
//        if (to > from) {
//            return create(from, from + 1, to);
//        }
//        throw new IllegalArgumentException();
        return null;
    }

    private Tuple create(int from, int mid, int to) {
//        var sub = code.substring(from + 1, to);
//
//        // 标签名称
//        var name = getName(sub.strip());
//        var property = sub.substring(name.length());
//
//        var label = Label.of(name, property);
//        var nex = fetch(mid, label);
//        finished = nex < 0;
//        if (finished) {
//            //<>
//            this.index = to + 1;
//            return new Singlet(label);
//        }
//
//        if (nex > to) {
//            // <> <
//            var strip = code.substring(to + 1, nex);
//            this.index = nex;
//            if (strip.isBlank()) {
//                return new Singlet(label);
//            }
//            return new Textile(label, strip);
//        }
//
//        return create(from, nex + 1, to);
        return null;
    }

    private int fetch(int mid, Label label) {
//        if (label instanceof Script) {
//            var stack = new Stack<>();
//            do {
//                var from = code.indexOf("<script", mid);
//                var to = code.indexOf("</script>", mid);
//                if (from > to){
//                    if (stack.isEmpty()){
//                        return to;
//                    }
//                    stack.pop();
//                    mid = from + 7;
//                    continue;
//                }
//                mid = to + 9;
//                stack.push(from);
//            } while (true);
//        }
//        return code.indexOf(Constant.PREFIX, mid);
        return 0;
    }

    private String getName(String txt) {
        var index = txt.indexOf(Constant.BLANK);
        if (index < 0) {
            return txt;
        }
        return txt.substring(0, index);
    }

    /**
     * 单独
     *
     * @param label
     */
    private record Singlet(Label label) implements Tuple {

        @Override
        public Label getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label.toString();
        }
    }

    private record Textile(Label label, String txt) implements Tuple {
        @Override
        public Label getLabel() {
            return label;
        }

        @Override
        public Text getText() {
            return new Text(txt);
        }

        @Override
        public String toString() {
            return label.toString() + txt;
        }
    }
}
