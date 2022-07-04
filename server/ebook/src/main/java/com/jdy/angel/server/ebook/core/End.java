package com.jdy.angel.server.ebook.core;

/**
 * @author Aglet
 * @create 2022/7/3 22:08
 */
public class End extends Label {

    private String name;

    @Override
    public void merge(Label pop) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public boolean isEnd(String text) {
        return true;
    }
}
