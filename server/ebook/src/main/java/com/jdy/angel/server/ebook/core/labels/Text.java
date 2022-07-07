package com.jdy.angel.server.ebook.core.labels;

/**
 * @author Aglet
 * @create 2022/7/5 19:01
 */
public class Text implements Label {

    private final String txt;

    public Text(String txt) {
        this.txt = txt;
    }

    @Override
    public boolean isEmpty() {
        return txt.isBlank();
    }

    @Override
    public String getPrefix() {
        throw new RuntimeException();
    }

    @Override
    public String getSuffix() {
        throw new RuntimeException();
    }

    @Override
    public boolean match(Label label) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.UNKNOWN;
    }

    @Override
    public String toString() {
        return txt;
    }

    @Override
    public String getDelimiter() {
        return "";
    }
}
