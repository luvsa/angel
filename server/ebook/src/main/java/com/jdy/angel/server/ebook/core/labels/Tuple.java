package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.server.ebook.core.Constant;

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

//    static Iterable<Tuple> iterable(String code) {
//        return new Tokenizer(code);
//    }

}
