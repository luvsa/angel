package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.utils.ArrayUtil;

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
    public boolean match(Label label) {
        if (label instanceof Block other) {
            var name = other.getName();
            return Objects.equals(name, this.name);
        }
        return false;
    }

    @Override
    public String getSuffix() {
        return "</" + name + ">";
    }

    @Override
    public boolean isFinished() {
        return name.startsWith("/");
    }

    private String getName() {
        if (name.startsWith("/")) {
            return name.substring(1);
        }
        return name;
    }

    @Override
    public boolean match(String name) {
        return Objects.equals(getName(), name);
    }

    @Override
    public Type getType() {
        if (isFinished()) {
            return Type.CLOSE;
        }
        return Type.OPEN;
    }

    @Override
    public String getDelimiter() {
        if (ArrayUtil.has(this::match, "a", "title", "li", "span")) {
            return "";
        }
        return Label.super.getDelimiter();
    }
}
