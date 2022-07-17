package com.jdy.angel.ebook.service;

import com.jdy.angel.ebook.core.net.Request;
import org.springframework.lang.NonNull;

/**
 * @author Aglet
 * @create 2022/7/12 23:00
 */
public interface ArticleService  {

	void start(String guid, String name, @NonNull Request domain);

}
