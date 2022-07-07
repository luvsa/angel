package com.jdy.angel.server.ebook.core;

import com.jdy.angel.utils.ArrayUtil;
import com.jdy.angel.utils.FileUtil;
import com.jdy.angel.utils.ReflectUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.luvsa.vary.other.Key;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

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

    @Test
    void split() {
        var clazz = Surname.class;
        var map = new HashMap<String, BiConsumer<Object, Object>>();
        var fields = clazz.getDeclaredFields();
        for (var field : fields) {
            var key = field.getAnnotation(Key.class);
            if (key == null) {
                continue;
            }
            var value = key.value();
            var method = ReflectUtil.search("set", field);
            if (method == null) {
                continue;
            }
            map.put(value, (o, args) -> {
                try {
                    method.invoke(o, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        var list = new ArrayList<Surname>();

        FileUtil.readResource("temp", s -> {
            var tree = new TreeMap<Integer, String>();
            for (var key : map.keySet()) {
                var index = s.indexOf(key);
                if (index < 0) {
                    continue;
                }
                tree.put(index, key);
            }

            var size = tree.size();
            if (size != 7) {
                return;
            }
            var surname = new Surname();
            var array = tree.keySet().toArray(new Integer[size]);
            for (int i = 0; i < size; i++) {
                var cur = array[i];
                var nex = i < size - 1 ? array[i + 1] : s.length();
                var txt = s.substring(cur, nex);
                var key = tree.get(cur);
                var value = txt.substring(key.length()).strip();
                if (ArrayUtil.has(value::startsWith, ":", ",", "=")) {
                    value = value.substring(1).strip();
                }
                var replace = value.replace("<br/>", "");
                if (replace.isBlank()) {
                    continue;
                }
                var consumer = map.get(key);
                if (consumer == null) {
                    continue;
                }
                consumer.accept(surname, replace);
            }
            list.add(surname);
        });
        System.out.println(list);
    }


    @Data
    public static class Surname {

        @Key("姓氏")
        private String name;

        @Key("祖籍")
        private String home;

        @Key("祖宗")
        private String ancestry;

        @Key("郡望")
        private String region;

        @Key("分类")
        private String type;

        @Key("历史名人")
        private String figures;

        @Key("姓氏来源")
        private String origin;
    }
}