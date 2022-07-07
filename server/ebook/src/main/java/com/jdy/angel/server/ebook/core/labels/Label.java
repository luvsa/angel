package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Constant;
import com.jdy.angel.server.ebook.core.Node;


/**
 * @author Aglet
 * @create 2022/7/5 18:22
 */
public interface Label {

    static Label of(String code) {
        var index = code.indexOf(Constant.BLANK);
        if (index < 0) {
            return of(code, Constant.EMPTY);
        }
        return of(code.substring(0, index).strip(), code.substring(index).strip());
    }

    static Label of(String name, String property) {
        return Util.create(name, property);
    }

    default void setProperty(String property) {

    }

    default void setName(String name) {
    }

    default boolean isFinished() {
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

    boolean match(Label label);

    default boolean match(String name) {
        return false;
    }

    default String get(String key) {
        return null;
    }

    Type getType();

    default int getChildTabs(int times) {
        return times + 1;
    }

    default String getDelimiter(){
        return "\n";
    }
}
