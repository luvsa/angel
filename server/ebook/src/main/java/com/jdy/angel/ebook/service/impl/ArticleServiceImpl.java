package com.jdy.angel.ebook.service.impl;

import com.jdy.angel.ebook.core.Constant;
import com.jdy.angel.ebook.core.Node;
import com.jdy.angel.ebook.core.Parser;
import com.jdy.angel.ebook.core.labels.Div;
import com.jdy.angel.ebook.core.net.Request;
import com.jdy.angel.ebook.entity.Article;
import com.jdy.angel.ebook.repository.ArticleRepository;
import com.jdy.angel.ebook.service.ArticleService;
import com.jdy.angel.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * repository
 *
 * @author Aglet
 * @create 2022/7/12 23:00
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Value("${angel.ebook.article.gene.identify:id='content'}")
    private String identify;

    @Value("${angel.ebook.article.gene.zip:false}")
    private boolean zip;

    @Value("${angel.ebook.article.content:content}")
    private String content;

    @Value("${angel.ebook.root}")
    private String root;

    private final ArticleRepository repository;

    private final TemplateEngine engine;

    public ArticleServiceImpl(ArticleRepository repository, TemplateEngine engine) {
        this.repository = repository;
        this.engine = engine;
    }

    private void next(Article article, ArrayList<Node> list) {
        if (!identify.contains("=")) {
            identify = "id='" + identify + "'";
        }
        try {
            var div = Div.of(identify);
            div.setZip(this.zip);
            var node = div.toNode();
            node.addAll(list);
//            var context = new Context();
//            context.setVariable("article", article);
//            context.setVariable("content", div);
//            var html = engine.process("article", context);
            var html = node.toString();
            saveFile(article, html);
        } catch (Exception e) {
            log.error("保存文件失败！", e);
        }
        repository.register(article);
    }

    private void saveFile(Article article, String html) {
        var priority = article.getPriority();
        var name = priority + Constant.EXTENSION;
        var path = Path.of(root, article.getBook(), name);
        var file = path.toFile();
        if (file.exists()) {
            if (priority < 0) {
                article.setPriority(priority - 1);
                saveFile(article, html);
                return;
            }
            throw new IllegalStateException("文件【" + file + "】已经存在！");
        }
        FileUtil.write(file, html);
        article.setPath(path.toString().replace(root, ""));
    }

    @Override
    public void start(String guid, String name, Request domain) {
        try {
            var priority = repository.getPriority(guid, name);
            if (priority.exists()) {
                return;
            }
            var article = new Article();
            article.setBook(guid);
            article.setTitle(name);
            article.setPriority(priority.value());

            Parser.remote(domain, node -> {
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
                next(article, list);
            });
        } catch (Exception e) {
            log.error("无法解析保存【{}】章节【{} <-> {}】数据", guid, name, domain.getDomain(), e);
            throw new RuntimeException(e);
        }
    }
}
