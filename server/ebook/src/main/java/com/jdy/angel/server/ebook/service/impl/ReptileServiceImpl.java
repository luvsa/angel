package com.jdy.angel.server.ebook.service.impl;

import com.jdy.angel.server.ebook.core.Parser;
import com.jdy.angel.server.ebook.core.net.Request;
import com.jdy.angel.server.ebook.service.BookService;
import com.jdy.angel.server.ebook.service.ReptileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * Reptile reptilian
 *
 * @author Aglet
 * @create 2022/7/3 19:43
 */
@Slf4j
@Service
public class ReptileServiceImpl implements ReptileService {

    public ReptileServiceImpl(BookService bookService) {
    }

    @Override
    public void start(Request domain) throws Exception {
        Parser.remote(domain, node -> {
            var content = node.fetch("content");
        });
    }
}
