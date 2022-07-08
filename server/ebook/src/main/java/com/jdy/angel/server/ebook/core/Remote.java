package com.jdy.angel.server.ebook.core;

import com.jdy.angel.utils.FileUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2022/7/7 14:31
 */
class Remote implements Parser {

    private final HttpClient client = HttpClient.newHttpClient();

    private URI uri;

    private final Tokenizer tokenizer = new Tokenizer();

    public Remote() {
    }

    public Remote(String url) {
        this.uri = URI.create(url);
    }

    @Override
    public void resolve(Consumer<Node> consumer) {
        resolve0(uri, consumer);
    }

    @Override
    public void resolve(String url, Consumer<Node> consumer) {
        resolve0(URI.create(url), consumer);
    }

    private void resolve0(URI uri, Consumer<Node> consumer) {
        //  解析 阶段
        var request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofMinutes(2))
                .build();

        var handler = HttpResponse.BodyHandlers.ofInputStream();
        var future = client.sendAsync(request, handler).whenComplete((response, throwable) -> {
            if (throwable == null) {
                FileUtil.read(response.body(), tokenizer);
                consumer.accept(tokenizer.get());
            }
            throw new RuntimeException(throwable);
        });

        try {
           var inputStreamHttpResponse = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
