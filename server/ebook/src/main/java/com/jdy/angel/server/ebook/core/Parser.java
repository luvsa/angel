package com.jdy.angel.server.ebook.core;

import com.jdy.angel.server.ebook.core.net.Request;

import java.util.function.Consumer;

/**
 * 代码解析器
 *
 * @author Aglet
 * @create 2022/7/6 20:48
 */
public interface Parser {

    static void resource(String file, Consumer<Node> consumer) throws Exception {
        Util.RESOURCE.resolve(file, consumer);
    }

    static void remote(Request request, Consumer<Node> consumer) throws Exception {
        Util.REMOTE.resolve(request.getDomain(), consumer);
    }

    /**
     * 解析资源文件
     *
     * @param file 文件名称
     * @return 解析器
     */
    static Parser fromResource(String file) {
        return new Resource(file);
    }

    static Parser fromRemote(String url) {
        return new Remote(url);
    }

    static Node resolve(String code) {
        var tokenizer = Util.DEFAULT;
        tokenizer.accept(code);
        return tokenizer.get();
    }

    void resolve(Consumer<Node> consumer);

    default void resolve(String source, Consumer<Node> consumer) throws Exception {
    }
}
