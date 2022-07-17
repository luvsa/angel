package com.jdy.angel.ebook.core.labels;

/**
 * @author Aglet
 * @create 2022/7/5 18:26
 */
@Sign("meta")
public class Meta extends Mark {

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Type getType() {
        return Type.END;
    }
}
