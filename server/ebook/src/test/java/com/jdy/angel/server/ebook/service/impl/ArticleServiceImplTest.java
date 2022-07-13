package com.jdy.angel.server.ebook.service.impl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author Aglet
 * @create 2022/7/12 23:40
 */
@SpringBootTest
class ArticleServiceImplTest {


    @Autowired
    TemplateEngine templateEngine;

    @Test
    void thymeleaf(){
        var context = new Context();
        context.setVariable("title", "Welcome angel index");
        String mail = templateEngine.process("article", context);
        System.out.println(mail);
    }



}