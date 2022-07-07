package com.jdy.angel.server.ebook.core;

import java.util.function.Consumer;

/**
 * 代码解析器
 *
 * @author Aglet
 * @create 2022/7/6 20:48
 */
public interface Parser {

    static Parser fromResource(String file) {
        return new ResourceParser(file);
    }

    static Node resolve(String code) {
        return Util.DEFAULT.apply(code);
    }

    Node get();

    default void forEach(Consumer<Node> consumer) {

    }
}
