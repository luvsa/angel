package com.jdy.angel.ebook.repository;

import com.jdy.angel.ebook.entity.Book;

/**
 * @author Dale
 * @create 2022/7/16 11:11
 */
public interface BookRepository {

	Book saveOrGet(Book book);

}
