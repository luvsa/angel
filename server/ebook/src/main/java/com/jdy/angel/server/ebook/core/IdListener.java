package com.jdy.angel.server.ebook.core;

import com.jdy.angel.server.ebook.core.labels.Label;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/12 22:21
 */
public class IdListener implements Listener{

    private final Map<String, Node> map = new HashMap<>();

    @Override
    public boolean test(String key) {
        return Objects.equals(key, "id");
    }

    @Override
    public void register(String key, Label mark) {
    }
}
