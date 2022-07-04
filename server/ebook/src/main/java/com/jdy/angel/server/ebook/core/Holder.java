package com.jdy.angel.server.ebook.core;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/3 21:50
 */
public class Holder extends Label {

    private final String text;

    public Holder(String text) {
        this.text = text;
    }

    @Override
    public void merge(Label pop) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Label getLabel(String text) {
        if (Objects.equals(text, "!DOCTYPE")){
            return new Doctype();
        }
        return new Start(text);
    }

    @Override
    public boolean match(Label other) {
        return false;
    }
}
