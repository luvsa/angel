package com.jdy.angel.server.ebook.core;

import org.junit.jupiter.api.Test;

/**
 * @author Aglet
 * @create 2022/7/3 22:37
 */
class TokenizerTest {

    @Test
    void start() {
        String code = """
                <!DOCTYPE html>
                
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta http-equiv="X-UA-Compatible" content="ie=edge">
                    <title>智能信息流一站式服务平台</title>
                    <meta name="keywords" content="易有效，youliao"/>
                    <link rel="stylesheet" href="css/main.css">
                </head>
                
                """;
        var tokenizer = new Tokenizer(code);
        var label = tokenizer.get();
        System.out.println(label);
    }
}