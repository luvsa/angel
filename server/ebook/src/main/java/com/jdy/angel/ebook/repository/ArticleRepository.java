package com.jdy.angel.ebook.repository;

import com.jdy.angel.ebook.dto.ArticlePriority;
import com.jdy.angel.ebook.entity.Article;

/**
 *
 * @author Dale
 * @create 2022/7/16 10:18
 */
public interface ArticleRepository {

  ArticlePriority getPriority(String guid, String name);

  void register(Article article);
}
