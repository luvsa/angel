package com.jdy.angel.ebook.core.labels;

import com.jdy.angel.ebook.core.Constant;

/**
 * @author Aglet
 * @create 2022/7/12 20:39
 */
public class Remark implements Label {

    private final String content;

    public Remark(String content) {
        this.content = content;
    }

    @Override
    public String getPrefix() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSuffix() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean match(Label label) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type getType() {
        return Type.UNKNOWN;
    }

    @Override
    public String toString() {
        return Constant.PREFIX + content + Constant.SUFFIX;
    }
}
