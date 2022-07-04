package com.jdy.angel.server.ebook.core;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.Flow.Subscriber;
import java.util.function.BiFunction;

/**
 * @author Aglet
 * @create 2022/7/3 21:09
 */
@Slf4j
public abstract class AbstractReptilian implements Reptilian {

    private final HttpClient client = HttpClient.newHttpClient();

    protected Subscriber<Success> subscriber;

    private final BiFunction<HttpResponse<String>, Throwable, String> function = (response, throwable) -> {
        if (throwable == null) {
            return response.body();
        }
        subscriber.onError(throwable);
        return null;
    };

    @Override
    public void start(URI domain) {
        var request = HttpRequest.newBuilder(domain)
                .timeout(Duration.ofMinutes(2))
                .build();
        var handler = BodyHandlers.ofString();
        client.sendAsync(request, handler).handleAsync(function).whenComplete((body, throwable) -> {
            if (throwable == null) {
                subscriber.onNext(new Success(domain, body));
            } else {
                subscriber.onError(throwable);
            }
        });
    }
}
