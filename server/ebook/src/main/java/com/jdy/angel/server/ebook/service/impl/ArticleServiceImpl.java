package com.jdy.angel.server.ebook.service.impl;

import com.jdy.angel.server.ebook.core.Constant;
import com.jdy.angel.server.ebook.core.Node;
import com.jdy.angel.server.ebook.core.Parser;
import com.jdy.angel.server.ebook.core.labels.Div;
import com.jdy.angel.server.ebook.core.net.Request;
import com.jdy.angel.server.ebook.entity.Article;
import com.jdy.angel.server.ebook.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.luvsa.vary.Vary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author Aglet
 * @create 2022/7/12 23:00
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Value("${ebook.article.content.id:id='content'}")
    private String identify;

    @Value("${ebook.article.content.zip:false}")
    private boolean zip;

    @Value("${ebook.article.remote.fetch:content}")
    private String content;

    private final TemplateEngine engine;

    private String root;

    public ArticleServiceImpl(TemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    public void start(Request request) throws Exception {
        Parser.remote(request, node -> {
            var list = new ArrayList<Node>();
            var target = node.fetch(this.content);
            var children = target.getChildren();
            for (var item : children) {
                var child = item.getChildren();
                if (child == null || child.size() != 1) {
                    continue;
                }
                var nod = child.get(0);
                if (nod.isText()) {
                    list.add(item);
                }
            }
            var article  = Vary.change(request, Article.class);
            if (article == null){
                throw new IllegalArgumentException();
            }
            next(article, list);
        });
    }

    private void next(Article article, ArrayList<Node> list) {
        var div = Div.of(identify);
        div.setZip(this.zip);
        var node = div.toNode();
        node.addAll(list);
        var priority = article.getPriority();
        var name = priority + Constant.EXTENSION;
        var path = Path.of(root, article.getBook(), name);
        var context = new Context();
        context.setVariable("article", article);
        context.setVariable("content", list);
        var html = engine.process("article", context);
        log.info(html);
    }
}
