package com.jdy.angel.ebook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdy.angel.ebook.entity.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Dale
 * @create 2021/11/5 20:49
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
