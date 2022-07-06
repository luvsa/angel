package com.jdy.angel.server.ebook.core;

import com.jdy.angel.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Aglet
 * @create 2022/7/5 21:21
 */
class ManagerTest {

    @Test
    void get() {
        String code = """
                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                <html>                
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta http-equiv="X-UA-Compatible" content="ie=edge">
                    <title>智能信息流一站式服务平台</title>
                    <meta name="keywords" content="易有效，youliao"/>
                    <link rel="stylesheet" href="css/main.css">
                </head>
                </html>            
                """;
        var manager = new Manager(code);
        var node = manager.get();
        System.out.println(node);
    }

    @ParameterizedTest
    @ValueSource(strings = {"index.html"})
    void read(String file) {
        var code = FileUtil.readResourceAsString(file);
        var manager = new Manager(code);
        var node = manager.get();
        System.out.println(node);
    }
}