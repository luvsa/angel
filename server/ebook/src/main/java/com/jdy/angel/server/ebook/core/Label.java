package com.jdy.angel.server.ebook.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/3 21:49
 */
public abstract class Label {

    private List<Label> children = new ArrayList<>();

    boolean finish;

    public void merge(Label pop) {
    }

    public abstract String getName();

    public String getText() {
        throw new RuntimeException();
    }

    public boolean match(Label other) {
        return Objects.equals(getName(), other.getName());
    }

    public boolean isEnd(String text) {
        return false;
    }

    public void add(List<Label> list) {
        for (Label label : list) {
            add(label);
        }
    }

    public void add(Label child) {
        children.add(child);
    }


    public Label getLabel(String text) {
        throw new RuntimeException(text);
    }

    public List<Label> getChildren() {
        return children;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public void pushKey(String text) {

    }
}
