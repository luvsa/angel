package com.jdy.angel.server.ebook.core;

/**
 * @author Aglet
 * @create 2022/7/3 23:02
 */
public class Doctype extends Label {

    private String other;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Label getLabel(String text) {
        finish = true;
        this.other = text;
        return this;
    }

    @Override
    public String toString() {
        return "<!DOCTYPE " + other + ">";
    }
}
