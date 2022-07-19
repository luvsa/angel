package com.jdy.angel.ebook.service.impl;

import com.jdy.angel.core.Dictionary;
import com.jdy.angel.ebook.core.Node;
import com.jdy.angel.ebook.core.Parser;
import com.jdy.angel.ebook.core.net.Request;
import com.jdy.angel.ebook.entity.Book;
import com.jdy.angel.ebook.repository.BookRepository;
import com.jdy.angel.ebook.security.WebConfiguration.BookConf;
import com.jdy.angel.ebook.service.ArticleService;
import com.jdy.angel.ebook.service.BookService;
import com.jdy.angel.service.DictService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.luvsa.lang.Strings;
import org.luvsa.reflect.Reflects;
import org.luvsa.vary.Vary;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Aglet
 * @create 2022/7/3 21:29
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final Map<String, BiConsumer<Object, String>> local = new HashMap<>();

    /**
     * 限流配置
     */
    private final RateLimiter rateLimiter = RateLimiter.of("NASDAQ :-)", RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofMillis(1000))
            .limitForPeriod(10)
            .timeoutDuration(Duration.ofMillis(1000))
            .build());

    private final ArticleService service;

    private final BookRepository repository;

    public BookServiceImpl(BookConf mapper, ArticleService service, BookRepository repository, DictService dict) {
        this.service = service;
        this.repository = repository;
        var maps = mapper.getMaps();
        ReflectionUtils.doWithLocalFields(Book.class, field -> {
            var fieldName = field.getName();
            var key = maps.get(fieldName);
            if (key == null) {
                return;
            }

            var set = Reflects.findMethod(Book.class, method -> {
                var methodName = method.getName();
                if (!methodName.startsWith("set")) {
                    return false;
                }
                var cur = Strings.uncapitalize(methodName.substring(3));
                return Objects.equals(cur, fieldName);
            });

            var dictionary = field.getAnnotation(Dictionary.class);


            local.put(key, (o, s) -> {
                var types = set.getParameterTypes();
                var args = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    var clz = types[i];
                    if (dictionary == null) {
                        args[i] = Vary.change(s, types[i]);
                    } else {
                        var name = dictionary.value();
                        if (name.isBlank()) {
                            name = fieldName;
                        }
                        if (dictionary.capital()) {
                            name = StringUtils.capitalize(name);
                        }
                        var search = dict.search(name, s);
                        if (search == null || search.isBlank()) {
                            args[i] = Vary.change(s, clz);
                        } else {
                            args[i] = Vary.change(search, clz);
                        }
                    }
                }
                try {
                    Reflects.invokeMethod(set, o, args);
                } catch (Exception e) {
                    log.error("放射方式设置[" + field + "]值出错！", e);
                }
            });
        });
    }

    @Override
    public void start(Request domain) throws Exception {
        var run = Try.run(() -> log.info("开始[{}]", domain.getDomain())).onFailure(throwable -> log.error("限流问题", throwable));
        Parser.remote(domain, node -> {
            var head = node.getNode("head");
            var book = analyse(head.getChildren());
            var origin = node.get("origin");
            //目录
            var children = node.fetch("list").getChildren().get(0).getChildren();
            for (var child : children) {
                var chi = child.getChildren();
                if (chi == null || chi.size() != 1) {
                    continue;
                }
                var nod = chi.get(0);
                var s = nod.get("href");
                if (s == null) {
                    continue;
                }

                var url = merge(origin, s);
                run.andThenTry(RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
                    service.start(book.getGuid(), nod.getChildren().get(0).toString(), () -> url);
                }));
            }
        });
    }

    private String merge(String uri, String url) {
        var builder = new StringBuilder();
        for (int i = 0, j = 0, size = uri.length(), len = url.length(); j < len; i++) {
            if (i < size) {
                var cur = uri.charAt(i);
                builder.append(cur);
                var tar = url.charAt(j);
                if (cur == tar) {
                    j++;
                } else {
                    j = 0;
                }
            } else {
                builder.append(url.charAt(j++));
            }
        }
        return builder.toString();
    }



    private Book analyse(List<Node> children) {
        var book = new Book();
        for (var child : children) {
            var label = child.getLabel();
            var property = label.get("property");
            var content = label.get("content");
            if (property == null || property.isBlank() || content == null || content.isBlank()) {
                continue;
            }
            var consumer = local.get(property);
            if (consumer != null) {
                consumer.accept(book, content);
            }
        }
        try {
            return repository.saveOrGet(book);
        } catch (Throwable e) {
			log.error("书籍信息【{}】保存失败！", book, e);
            throw new RuntimeException("书籍信息【" + book + "】保存失败！", e);
        }
    }
}
