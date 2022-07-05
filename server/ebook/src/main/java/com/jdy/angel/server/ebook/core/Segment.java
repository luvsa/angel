package com.jdy.angel.server.ebook.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/4 13:06
 */
public abstract class Segment {

    private List<Segment> children;

    abstract String getName();

    public boolean isFinished() {
        return false;
    }

    public void merge(Segment block) {
        var text = getText();
        setText(text);
        if (match(block)) {
            return;
        }
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(block);
    }

    protected Text getText() {
        return null;
    }

    protected boolean match(Segment block) {
        return Objects.equals(getName(), block.getName());
    }


    protected void setText(Text txt) {
        if (txt == null){
            return;
        }
        children.add(txt);
    }
}
