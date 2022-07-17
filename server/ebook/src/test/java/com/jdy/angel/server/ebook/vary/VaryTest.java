package com.jdy.angel.server.ebook.vary;

import com.jdy.angel.ebook.core.net.Request;
import com.jdy.angel.ebook.entity.Article;
import org.junit.jupiter.api.Test;
import org.luvsa.vary.Vary;

/**
 * @author Aglet
 * @create 2022/7/13 0:09
 */
class VaryTest {

    @Test
    void reqToArticle(){

        var request = new Request() {
            @Override
            public String getDomain() {
                return "127.0.0.1";
            }
        };

        var article = Vary.change(request, Article.class);
        System.out.println(article);
    }

}