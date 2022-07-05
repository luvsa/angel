package com.jdy.angel.server.ebook.service.impl;

import com.jdy.angel.server.ebook.service.BookService;
import com.sun.net.httpserver.Authenticator.Success;
import org.springframework.stereotype.Service;

import java.util.concurrent.Flow.Subscription;

/**
 * @author Aglet
 * @create 2022/7/3 21:29
 */
@Service
public class BookServiceImpl implements BookService {

    @Override
    public void onSubscribe(Subscription subscription) {
    }

    @Override
    public void onNext(Success item) {
//        var body = item.body();
//        var uri = item.uri();
//        Resolver.accept(body, o -> {
//        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
