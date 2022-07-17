package com.jdy.angel.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jdy.angel.data.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author Aglet
 * @create 2022/7/12 23:21
 */
@Data
@TableName("Article")
@EqualsAndHashCode(callSuper = true)
public class Article extends Entity {
    @Serial
    private static final long serialVersionUID = -6627035042653813636L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 序号， 用于排序, 优先级
     */
    private Integer priority;

    private String book;

}
