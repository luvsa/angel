package com.jdy.angel.server.ebook.core.labels;

/**
 * @author Aglet
 * @create 2022/7/12 22:47
 */
@Sign("div")
public class Div extends Mark {

    private boolean zip;

    public Div() {
        this.name = "div";
    }

    public static Div of(String property) {
        var mark = new Div();
        mark.setProperty(property);
        return mark;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    @Override
    public String getDelimiter() {
        return zip ? "" : super.getDelimiter();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
