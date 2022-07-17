package com.jdy.angel.ebook.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdy.angel.ebook.entity.Book;
import com.jdy.angel.ebook.mapper.BookMapper;
import com.jdy.angel.ebook.repository.BookRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dale
 * @create 2022/7/16 11:11
 */
@Repository
public class BookRepositoryImpl extends ServiceImpl<BookMapper, Book> implements BookRepository {
	@Override
	public Book saveOrGet(Book book) {
		var wrapper = new QueryWrapper<Book>();
		wrapper.eq("name", book.getName());
		wrapper.eq("author", book.getAuthor());
		try {
			var one = getOne(wrapper);
			if (one == null) {
				throw new IllegalStateException("请保存图书！");
			}
			return one;
		} catch (Exception e) {
			if (!save(book)) {
				throw new IllegalStateException("保存图书[" + book + "]失败！", e);
			}
		}
		return book;
	}
}
