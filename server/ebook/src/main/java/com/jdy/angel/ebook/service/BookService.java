package com.jdy.angel.ebook.service;

import com.jdy.angel.ebook.core.net.Request;
import com.jdy.angel.ebook.core.net.Sender;
import org.springframework.lang.NonNull;

/**
 * @author Aglet
 * @create 2022/7/3 21:28
 */
public interface BookService extends Sender {

	void start(@NonNull Request domain) throws Exception;

}
