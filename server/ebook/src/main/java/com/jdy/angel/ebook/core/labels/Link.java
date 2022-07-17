package com.jdy.angel.ebook.core.labels;

/**
 * @author Aglet
 * @create 2022/7/5 18:27
 */
@Sign("link")
public class Link extends Mark {
    @Override
    public Type getType() {
        return Type.END;
    }
}
