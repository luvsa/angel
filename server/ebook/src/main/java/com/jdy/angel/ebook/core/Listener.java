package com.jdy.angel.ebook.core;

import com.jdy.angel.ebook.core.labels.Label;

/**
 * @author Aglet
 * @create 2022/7/12 22:04
 */
public interface Listener {

    boolean test(String key);

    void register(String key, Label mark);
}
