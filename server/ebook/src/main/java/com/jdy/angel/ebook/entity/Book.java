package com.jdy.angel.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jdy.angel.data.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Dale
 * @create 2022/7/16 0:28
 */
@Data
@TableName("Book")
@EqualsAndHashCode(callSuper = true)
public class Book extends Entity {

	/**
	 * 图书名称
	 */
	private String name;

	/**
	 * 封面图片
	 */
	private String avatar;

	/**
	 * 图书作者
	 */
	private String author;

	/**
	 * 图书类型
	 */
	private int type;

	/**
	 * 内容类型
	 */
	private String kind;

	/**
	 * 图书简介
	 */
	private String desc;

	/**
	 * 图书状态
	 */
	private String status;
}
