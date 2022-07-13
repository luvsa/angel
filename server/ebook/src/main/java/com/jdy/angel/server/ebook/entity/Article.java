package com.jdy.angel.server.ebook.entity;

import lombok.Data;

/**
 * @author Aglet
 * @create 2022/7/12 23:21
 */
@Data
public class Article {
    /**
     * 文章标题
     */
    private String title;

    /**
     * 序号， 用于排序, 优先级
     */
    private long priority;

    private String book;

}
