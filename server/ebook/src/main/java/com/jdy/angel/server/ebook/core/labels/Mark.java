package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Constant;
import com.jdy.angel.server.ebook.core.Listener;

import java.util.*;

/**
 * @author Aglet
 * @create 2022/7/5 20:23
 */
public class Mark extends Block {

    protected Map<String, String> attributes;

    private boolean finished;

    private final List<Listener> listeners = new ArrayList<>();

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
                for (var item: listeners) {
                    if (item.test(key)){
                        item.register(value, this);
                    }
                }
                attributes.put(key, value);
            } catch (Exception e) {
//                System.out.println(property);
            }
        } while (true);
    }

    @Override
    public String getPrefix() {
        var suffix = finished ? " />" : Constant.SUFFIX;
        var joiner = new StringJoiner(Constant.BLANK, Constant.PREFIX, suffix);
        joiner.add(name);
        if (attributes != null) {
            attributes.forEach((s, s2) -> joiner.add(s + "=" + s2));
        }
        return joiner.toString();
    }

    @Override
    public void register(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public String toString() {
        var tail = this.tail ? Constant.PREFIX + Constant.DIV + name + Constant.SUFFIX : Constant.EMPTY;
        return getPrefix() + tail;
    }

    @Override
    public String get(String key) {
        if (attributes == null) {
            return null;
        }
        var s = attributes.get(key);
        if (s == null) {
            return null;
        }
        return s.substring(1, s.length() - 1);
    }

    @Override
    public Type getType() {
        if (finished) {
            return Type.END;
        }
        return super.getType();
    }
}
