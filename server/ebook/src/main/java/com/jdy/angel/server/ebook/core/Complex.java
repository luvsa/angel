package com.jdy.angel.server.ebook.core;

/**
 * @author Aglet
 * @create 2022/7/4 17:02
 */
public class Complex extends Segment {

    private String txt;

    private Segment nex;

    public Complex(String prefix, Segment segment) {
        this.txt = prefix;
        this.nex = segment;
    }

    @Override
    String getName() {
        return nex.getName();
    }

    @Override
    public boolean isFinished() {
        return nex.isFinished();
    }

    @Override
    protected Text getText() {
        return new Text(txt);
    }

    @Override
    public void merge(Segment block) {
        if (nex.match(block)) {
            nex.setText(block.getText());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
