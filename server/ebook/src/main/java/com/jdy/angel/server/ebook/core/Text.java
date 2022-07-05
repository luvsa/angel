package com.jdy.angel.server.ebook.core;

/**
 * 文本内容
 *
 * @author Aglet
 * @create 2022/7/4 17:30
 */
public class Text extends Segment implements Mark {

    private final String txt;

    public Text(String txt) {
        this.txt = txt;
    }

    @Override
    String getName() {
        return txt;
    }

    @Override
    public String toString() {
        return txt;
    }
}
