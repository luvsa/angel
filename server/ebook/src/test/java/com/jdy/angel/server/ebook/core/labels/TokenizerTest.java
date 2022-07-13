package com.jdy.angel.server.ebook.core.labels;


import com.jdy.angel.server.ebook.core.Node;
import com.jdy.angel.server.ebook.core.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author Aglet
 * @create 2022/7/5 20:05
 */
class TokenizerTest {

    @ParameterizedTest
    @ValueSource(strings = {"https://www.31xiaoshuo.com/182/182821/73241501.html"})
    void remote(String url) throws ExecutionException, InterruptedException {
        var parser = Parser.fromRemote(url);
        parser.resolve(node -> {
            System.out.println(node);
        });
    }


    @Test
    void start() {
        var code = """
                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">    
                <head>
                    <meta />
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta http-equiv="X-UA-Compatible" content="ie=edge">
                    <title>智能信息流一站式服务平台</title>
                    <meta name="keywords" content="易有效，youliao"/>
                    <link rel="stylesheet" href="css/main.css"/>
                </head>      
                """;
        var node = Parser.resolve(code);
        System.out.println(node);
    }

    @ParameterizedTest
    @ValueSource(strings = {"index.html"})
    void read(String file) {
        var parser = Parser.fromResource(file);
        var list = new ArrayList<Node>();
        parser.resolve(node -> {
            var content = node.fetch("content");
            var children = content.getChildren();
            for (var item : children) {
                var child = item.getChildren();
                if (child == null|| child.size() != 1){
                    continue;
                }
                var nod = child.get(0);
                if (nod.isText()){
                    list.add(item);
                }
            }
        });
        var mark = Div.of("id='content'");
        var node = mark.toNode();
        node.addAll(list);
        // 段落

        System.out.println(node.toString());
    }
}