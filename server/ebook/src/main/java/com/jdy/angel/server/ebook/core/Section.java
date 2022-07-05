package com.jdy.angel.server.ebook.core;

/**
 * @author Aglet
 * @create 2022/7/5 13:00
 */
public class Section {
    /**
     * 前缀
     */
    private final String prefix;

    /**
     * 名称
     */
    private final String name;

    /**
     * 属性 property
     */
    private final String property;

    public Section(String prefix, String label) {
        this.prefix = prefix.strip();
        var index = label.indexOf(" ");
        if (index < 0) {
            this.property = Constant.EMPTY;
            this.name = label.strip();
        } else {
            this.name = label.substring(0, index).strip();
            this.property = label.substring(index).strip();
        }
    }


//    private Segment create0() {
//        if (Objects.equals(name, Doctype.SIGN)) {
//            return new Doctype(content);
//        }
//
//        var stack = new Stack<Integer>();
//        var size = content.length();
//        var map = new ConcurrentHashMap<String, String>();
//
//        var key = "";
//        for (int i = 0, j = 0; i < size; i++) {
//            var c = content.charAt(i);
//            if (c == '"' || c == '\'') {
//                if (stack.isEmpty()) {
//                    stack.push(i);
//                } else {
//                    var pop = stack.pop();
//                    var pre = content.charAt(pop);
//
//                    if (pre == c) {
//                        var value = content.substring(pop, i + 1).strip();
//                        j = i + 1;
//                        if (key.isBlank()) {
//                            throw new IllegalArgumentException();
//                        }
//                        map.put(key, value);
//                        key = "";
//                    }
//                }
//            } else if (c == '=') {
//                if (stack.isEmpty()) {
//                    key = content.substring(j, i).strip();
//                    j = i + 1;
//                }
//            }
//        }
//
//        if (Meta.SIGN.equalsIgnoreCase(name)) {
//            return new Meta(map);
//        }
//        return new Label(name, map);
//    }


    @Override
    public String toString() {
        return prefix + Constant.PREFIX + name + Constant.BLANK + property + Constant.SUFFIX;
    }
}
