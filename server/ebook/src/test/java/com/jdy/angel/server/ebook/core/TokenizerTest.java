package com.jdy.angel.server.ebook.core;

import com.jdy.angel.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Aglet
 * @create 2022/7/3 22:37
 */
class TokenizerTest {

    @Test
    void start() {
        String code = """
                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                                
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
        for (var item : tokenizer) {
            System.out.println(item);
        }
    }

    @Test
    void regex() {
//        var txt = "meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"";
//        var section = new Section("", txt);
//        var label = section.create();
//        System.out.println(label);
    }

    @ParameterizedTest
    @ValueSource(strings = {"index.html"})
    void read(String file) {
        var code = FileUtil.readResourceAsString(file);
        var tokenizer = new Tokenizer(code);
        for (var item : tokenizer) {
            System.out.println(item);
        }
    }
}