package com.jdy.angel.server.ebook.core;

import java.util.Map;
import java.util.StringJoiner;

/**
 * @author Aglet
 * @create 2022/7/4 15:14
 */
public class Meta extends  Block {

    final static String SIGN = "meta";

    public Meta(Map<String, String> map) {
        super(map);
    }

    @Override
    public String getName() {
        return SIGN;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public String toString() {
        var joiner = new StringJoiner(" ", "<", ">");
        joiner.add(SIGN);
        attributes.forEach((s, s2) -> joiner.add(s + "=" + s2));
        return joiner.toString();
    }
}
