package com.jdy.angel.vary.request;

import com.jdy.angel.ebook.core.net.Request;
import com.jdy.angel.ebook.entity.Article;
import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/13 0:02
 */
@Types(Article.class)
public class ToArticle implements RProvider {

    @Override
    public Function<Request, ?> get(Type type) {
        return item -> {
            return null;
        };
    }

}