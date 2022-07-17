package com.jdy.angel.ebook.core;

import com.jdy.angel.utils.FileUtil;

import java.util.function.Consumer;

/**
 * 基于文件实现
 *
 * @author Aglet
 * @create 2022/7/6 20:48
 */
class Resource implements Parser {

    /**
     * 文件路径
     */
    private String resource;

    private final Tokenizer tokenizer;

    public Resource() {
        this.tokenizer = new Tokenizer();
    }

    public Resource(String resource) {
        this.resource = resource;
        this.tokenizer = new Tokenizer();
    }

    public void resolve(Consumer<Node> consumer) {
        resolve(resource, consumer);
    }

    @Override
    public void resolve(String source, Consumer<Node> consumer) {
        if (source == null || source.isBlank()){
            throw new IllegalArgumentException();
        }
        FileUtil.readResource(source, tokenizer);
        consumer.accept(tokenizer.get());
    }
}
