package com.jdy.angel.server.ebook.core;

/**
 * @author Aglet
 * @create 2022/7/3 22:09
 */
public class Start extends Label {

    private String text;

    private final String name;

    public Start(String name) {
        this.name = name;
    }

    @Override
    public void merge(Label pop) {
        this.text = pop.getText();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void add(Label peek) {

    }

    @Override
    public String toString() {
        var prefix = '<' + name;
        if (text == null) {
            return prefix + " />";
        }
        prefix += '>';
        var suffix = "</ " + name + '>';
        return prefix + text + suffix;
    }
}
