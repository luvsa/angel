package com.jdy.angel.server.ebook.service.impl;

import com.jdy.angel.server.ebook.core.Parser;
import com.jdy.angel.server.ebook.core.net.Request;
import com.jdy.angel.server.ebook.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Aglet
 * @create 2022/7/3 21:29
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Override
    public void start(Request domain) throws Exception {
        Parser.remote(domain, node -> {
            log.info(node.toString());
        });
    }

}
