package com.jdy.angel.server.ebook.core;

import java.util.StringJoiner;

/**
 * @author Aglet
 * @create 2022/7/3 23:02
 */
public class Doctype extends Segment {

    final static String SIGN = "!DOCTYPE";

    private final String attr;

    public Doctype(String illustrates) {
        this.attr = illustrates;
    }

    @Override
    String getName() {
        return SIGN;
    }

    @Override
    public String toString() {
        var joiner = new StringJoiner(" ", "<", ">");
        joiner.add(SIGN);
        joiner.add(attr);
        return joiner.toString();
    }
}
