package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Kind;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/5 20:10
 */
abstract class Block implements Label {

    protected String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }

    @Override
    public Kind match(Label label) {
        if (label instanceof Block other) {
            var name = other.getName();
            if (Objects.equals(name, this.name)) {
                return Kind.matched;
            }
        }

        if (this instanceof Head && label instanceof Meta) {
            return Kind.half;
        }
        return Kind.Unmatched;
    }

    @Override
    public String getSuffix() {
        return "</" + name + ">";
    }

    @Override
    public boolean isOff() {
        return name.startsWith("/");
    }

    private String getName() {
        if (name.startsWith("/")) {
            return name.substring(1);
        }
        return name;
    }
}
