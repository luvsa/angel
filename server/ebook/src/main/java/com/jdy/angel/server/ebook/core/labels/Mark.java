package com.jdy.angel.server.ebook.core.labels;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringJoiner;

/**
 * @author Aglet
 * @create 2022/7/5 20:23
 */
class Mark extends Block {

    protected Map<String, String> attributes;

    private boolean finished;

    @Override
    public void setProperty(String property) {
        if (property.isBlank()) {
            return;
        }
        attributes = new HashMap<>();
        int from = 0, size = property.length();
        var stack = new Stack<Integer>();
        do {
            var index = property.indexOf("=", from);
            if (index < 0) {
                finished = property.endsWith("/");
                break;
            }
            var key = property.substring(from, index).strip();
            from = index + 1;
            for (; from < size; from++) {
                var c = property.charAt(from);
                if (c == '"' || c == '\'') {
                    if (stack.isEmpty()) {
                        stack.push(from);
                        continue;
                    }
                    var peek = stack.peek();
                    var pre = property.charAt(peek);
                    if (pre != c) {
                        stack.push(from);
                        continue;
                    }
                    stack.pop();
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }
            try {
                var value = property.substring(index + 1, ++from).strip();
                attributes.put(key, value);
            } catch (Exception e) {
//                System.out.println(property);
            }

        } while (true);
    }

    @Override
    public String getPrefix() {
        return toString();
    }


    @Override
    public String toString() {
        var suffix = finished ? " />" : ">";
        var joiner = new StringJoiner(" ", "<", suffix);
        joiner.add(name);
        if (attributes != null) {
            attributes.forEach((s, s2) -> joiner.add(s + "=" + s2));
        }
        return joiner.toString();
    }

    @Override
    public String get(String key) {
        var s = attributes.get(key);
        if (s == null) {
            return null;
        }
        return s.substring(1, s.length() - 1);
    }

    @Override
    public Type getType() {
        if (finished){
            return Type.END;
        }
        return super.getType();
    }
}
