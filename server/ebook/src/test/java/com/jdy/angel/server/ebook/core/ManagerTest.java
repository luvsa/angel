package com.jdy.angel.server.ebook.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdy.angel.utils.ArrayUtil;
import com.jdy.angel.utils.FileUtil;
import com.jdy.angel.utils.ReflectUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.luvsa.vary.other.Key;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

/**
 * @author Aglet
 * @create 2022/7/5 21:21
 */
class ManagerTest {
    private final HttpClient client = HttpClient.newHttpClient();

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
    void split() throws JsonProcessingException, ExecutionException, InterruptedException {
        var clazz = Surname.class;
        var map = new HashMap<String, BiConsumer<Object, Object>>();

        var fields = clazz.getDeclaredFields();
        for (var field : fields) {
            var key = field.getAnnotation(Key.class);
            if (key == null) {
                continue;
            }
            var value = key.value();

            var method = ReflectUtil.getSetMethod(field);
//                    ReflectUtil.searchSet("set", field);
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
            if (size < 3) {
//                System.out.println(s);
                return;
            }
            System.out.println(s);
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
                var replace = value.replace("<br/>", "").replace("：", "");
                if (replace.isBlank()) {
                    continue;
                }
                var consumer = map.get(key);
                if (consumer == null) {
                    continue;
                }
                consumer.accept(surname, replace.strip());
            }
            list.add(surname);
        });






    }


    @Test
    void gen() throws JsonProcessingException, ExecutionException, InterruptedException {
        /*
姓氏：敖      <br/>
1、源于上古，是古帝颛顼的老师太敖的后代。太敖，一做大敖。太敖的子孙以祖上的名字命名他们的姓氏，于是形成了敖姓。 <br/>
2、出自芈姓。春秋时期的楚国国君，凡是被废弑而没有得到谥号的，都被称为敖，这类国君的后代，也被称为敖氏。 <br/>

历史名人：<br/>
敖山：字静之。明朝时期莘县人。成化进士，由翰林院编修升任山西提学副使。后因疾病辞官还乡。工诗文，诗才雄爽，文章豪放，与当时的王越齐名，人称江北二杰。晚年的时候专心研究数学。著有《石绫传》、《灿然稿》、《先天手册》。 <br/>
敖家熊：字孟姜。浙江平湖人。早年在嘉兴创办稼公社及竹木小学堂。并组织祖宗教。清光绪二十九年入上海爱国学社学习。曾编写《新山歌》一书，宣传革命。次年加入光复会，并且出资与魏兰等人组织温台处会馆，这是革命党人的一个秘密联系机关。光绪三十三年与秋瑾共谋在大通师范学堂起义，事情泄露后逃跑。光绪三十四年在嘉兴被仇敌杀害。 <br/>
敖陶孙：字器之，宋朝时期福清人。从小聪明好学，志向远大。当时奸臣韩佗胄当权，大儒朱熹遭贬，而陶孙很尊敬朱熹的学问，于是去探望了他，并且赠诗表明自己的心意。赵汝愚死在被贬的地方，他又写诗哭赵。韩佗胄知道以后大怒，要逮捕他。陶孙改名换姓逃掉了。后来陶孙中了进士，做了温州通判，著有《寀庵集》。 <br/>
敖英：明代正德进士，字子发，清江人。官至江西右部正使。工于诗。他的诗路独辟蹊径，很有特点，流传于世的有《绿学亭杂言》。


        */

        var surname = new Surname();
        surname.setName("敖");
//        surname.setHome("山东");
//        surname.setAncestry("出自姬姓");
//        surname.setType("以居住地命姓");

        surname.setOrigin(
                """
                1、源于上古，是古帝颛顼的老师太敖的后代。太敖，一做大敖。太敖的子孙以祖上的名字命名他们的姓氏，于是形成了敖姓。
                2、出自芈姓。春秋时期的楚国国君，凡是被废弑而没有得到谥号的，都被称为敖，这类国君的后代，也被称为敖氏。
                """
        );

        surname.setRegion("东门氏望出济阳郡。晋惠帝时，将陈留郡之一部分置济阳郡，相当于现在兰考县一带地区。");

        surname.setFigures("""
                敖山：字静之。明朝时期莘县人。成化进士，由翰林院编修升任山西提学副使。后因疾病辞官还乡。工诗文，诗才雄爽，文章豪放，与当时的王越齐名，人称江北二杰。晚年的时候专心研究数学。著有《石绫传》、《灿然稿》、《先天手册》。
                敖家熊：字孟姜。浙江平湖人。早年在嘉兴创办稼公社及竹木小学堂。并组织祖宗教。清光绪二十九年入上海爱国学社学习。曾编写《新山歌》一书，宣传革命。次年加入光复会，并且出资与魏兰等人组织温台处会馆，这是革命党人的一个秘密联系机关。光绪三十三年与秋瑾共谋在大通师范学堂起义，事情泄露后逃跑。光绪三十四年在嘉兴被仇敌杀害。
                敖陶孙：字器之，宋朝时期福清人。从小聪明好学，志向远大。当时奸臣韩佗胄当权，大儒朱熹遭贬，而陶孙很尊敬朱熹的学问，于是去探望了他，并且赠诗表明自己的心意。赵汝愚死在被贬的地方，他又写诗哭赵。韩佗胄知道以后大怒，要逮捕他。陶孙改名换姓逃掉了。后来陶孙中了进士，做了温州通判，著有《寀庵集》。
                敖英：明代正德进士，字子发，清江人。官至江西右部正使。工于诗。他的诗路独辟蹊径，很有特点，流传于世的有《绿学亭杂言》。
                """);
//
//        surname.setRemark("复姓'东门'源头在春秋时期的鲁国。春秋时鲁庄公的庶子公子遂，字襄仲，原为姬姓，因居于东门，号东门襄仲，因以所居'东门'为氏，距今已有2500多年的历史。");
        var list = List.of(surname);
        send(list);
    }


    private void send(List<Surname> list) throws JsonProcessingException, ExecutionException, InterruptedException {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(list);
        var uri = URI.create("http://localhost:8080/surname/batch");
        var request = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMinutes(2))
                .build();
        var handler = HttpResponse.BodyHandlers.ofString();
        var future = client.sendAsync(request, handler);

        var response = future.get();
        var body = response.body();
        System.out.println(body);
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

        private String remark;

        public String toBody() {
            return "name=" + this.name + "&home=" + home + "&ancestry=" + ancestry + "&region=" + region;
        }
    }
}