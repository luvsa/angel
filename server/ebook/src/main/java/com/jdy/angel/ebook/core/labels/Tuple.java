package com.jdy.angel.ebook.core.labels;

import com.jdy.angel.ebook.core.Constant;

/**
 * @author Aglet
 * @create 2022/7/5 18:42
 */
public interface Tuple {

    Text NONE = new Text(Constant.EMPTY);

    Label getLabel();

    default Text getText() {
        return NONE;
    }
}
