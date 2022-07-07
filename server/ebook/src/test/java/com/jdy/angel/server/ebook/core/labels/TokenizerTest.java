package com.jdy.angel.server.ebook.core.labels;


import com.jdy.angel.server.ebook.core.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Aglet
 * @create 2022/7/5 20:05
 */
class TokenizerTest {

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

//        var tokenizer = new Tokenizer(code);
//        for (var item : tokenizer) {
//            System.out.println(item);
//        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"index.html"})
    void read(String file) {
        var parser = Parser.fromResource(file);
        var node = parser.get();
        System.out.println(node);

//
//        FileUtil.readResource(file, new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });


//
//        var tokenizer = new Tokenizer(code);
//        for (var item : tokenizer) {
//            System.out.println(item);
//        }
    }
}