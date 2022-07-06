package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Kind;
import com.jdy.angel.server.ebook.core.Node;


/**
 * @author Aglet
 * @create 2022/7/5 18:22
 */
public interface Label {

    static Label of(String name, String property) {
        return Util.create(name, property);
    }

    default void setProperty(String property) {
    }

    default void setName(String name) {
    }

    default boolean isOff() {
        return false;
    }

    default Node toNode() {
        return new Node() {
            @Override
            protected Label getValue() {
                return Label.this;
            }
        };
    }

    default boolean isEmpty() {
        return false;
    }

    String getPrefix();

    String getSuffix();

    Kind match(Label label);

    default String get(String key) {
        return null;
    }
}
