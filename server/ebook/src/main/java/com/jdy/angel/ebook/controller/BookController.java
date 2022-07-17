package com.jdy.angel.ebook.controller;

import com.jdy.angel.ebook.service.BookService;
import com.jdy.angel.net.Response;
import com.jdy.angel.net.Responses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aglet
 * @create 2022/7/13 0:39
 */
@Slf4j
@RestController
@RequestMapping("book")
public record BookController(BookService service) {

    @GetMapping("start")
    public Response fetch(String url) {
        try {
            service.start(() -> url);
            return Responses.ok("开始爬取： " + url + " 内容！");
        } catch (Exception e) {
            throw new IllegalArgumentException("无法解析： " + url, e);
        }
    }

}
