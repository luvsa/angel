package com.jdy.angel.server.ebook.vary.request;

import com.jdy.angel.server.ebook.core.net.Request;
import com.jdy.angel.server.ebook.entity.Article;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/13 0:02
 */
@Types(Article.class)
public class ToArticle implements RProvider {

    @Override
    public Function<Request, ?> get(Class<?> clazz) {
        return item -> {
            return null;
        };
    }
}