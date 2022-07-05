package com.jdy.angel.server.ebook.core;


import java.util.Map;
import java.util.StringJoiner;

/**
 * Block Segment section
 *
 * @author Aglet
 * @create 2022/7/3 21:49
 */
public class Label extends Block {

    private final String name;

    public Label(String name, Map<String, String> map) {
        super(map);
        this.name = name;
    }

    @Override
    String getName() {
        if (isFinished()){
            return name.substring(1);
        }
        return name;
    }

    @Override
    public boolean isFinished() {
        return name.startsWith("/");
    }

    @Override
    public String toString() {
        var joiner = new StringJoiner(" ", "<", ">");
        joiner.add(name);
        attributes.forEach((s, s2) -> joiner.add(s + "=" + s2));
        return joiner.toString();
    }
}
