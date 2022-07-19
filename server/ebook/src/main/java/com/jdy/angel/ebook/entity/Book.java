package com.jdy.angel.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jdy.angel.core.Dictionary;
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
	@Dictionary(capital = true)
	private int type;

	/**
	 * 内容类型
	 */
	@Dictionary(capital = true)
	private int kind;

	/**
	 * 图书简介
	 */
	private String description;

	/**
	 * 图书状态
	 */
	@Dictionary(capital = true)
	private int status;
}
