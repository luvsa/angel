package com.jdy.angel.server.ebook.service.impl;

import com.jdy.angel.server.ebook.core.AbstractReptilian;
import com.jdy.angel.server.ebook.service.BookService;
import com.jdy.angel.server.ebook.service.ReptileService;
import org.springframework.stereotype.Service;

/**
 * Reptile reptilian
 * @author Aglet
 * @create 2022/7/3 19:43
 */
@Service
public class ReptileServiceImpl extends AbstractReptilian implements ReptileService {

    public ReptileServiceImpl(BookService bookService) {
        this.subscriber = bookService;
    }

}
