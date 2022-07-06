package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Constant;

import java.util.StringJoiner;

/**
 * 说明书 manual
 *
 * @author Aglet
 * @create 2022/7/3 23:02
 */
@Sign("!DOCTYPE")
public class Doctype extends Block {

    private String attr;

    @Override
    public void setProperty(String property) {
        this.attr = property;
    }

    @Override
    public String getPrefix() {
        return toString();
    }

    @Override
    public String getSuffix() {
        return Constant.EMPTY;
    }

    @Override
    public String toString() {
        var joiner = new StringJoiner(" ", "<", ">");
        joiner.add(name);
        joiner.add(attr);
        return joiner.toString();
    }


}
