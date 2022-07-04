package com.jdy.angel.server.ebook.core;

/**
 * @author Aglet
 * @create 2022/7/3 23:20
 */
public class Key extends Label {

    private Label base;

    private String key;

    public Key(Label pop, String text) {
        this.base = pop;
        this.key = text;
    }

    @Override
    public String getName() {
        return null;
    }
}
