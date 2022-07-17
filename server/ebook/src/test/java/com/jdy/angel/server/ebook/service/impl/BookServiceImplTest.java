package com.jdy.angel.server.ebook.service.impl;

import com.jdy.angel.ebook.core.Parser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Aglet
 * @create 2022/7/13 1:05
 */
@Slf4j
class BookServiceImplTest {

	@Test
	void start() throws Exception {
		Parser.remote(() -> "https://www.31xiaoshuo.com/182/182821/", node -> {
			var origin = node.get("origin");
			System.out.println(origin);
		});
		TimeUnit.SECONDS.sleep(10);
	}

	@Test
	void parser() throws Exception {
		Parser.resource("book.html", node -> {
//            log.info(node.toString());
//			var head = node.getNode("head");
//			var book = Vary.change(head.getChildren(), Book.class);

//			var origin = node.get("origin");

			//目录
			var children = node.fetch("list").getChildren().get(0).getChildren();
			for (var child : children) {
				var chi = child.getChildren();
				if (chi == null || chi.size() != 1) {
					continue;
				}
				var nod = chi.get(0);
				var s = nod.get("href");
				if (s == null) {
					continue;
				}
				next(nod.getChildren().get(0).toString(), s);
			}
		});
	}

	private void next(String name, String url) {
		log.info("name : {};  url : {}", name, url);
	}

	@Test
	void test() {
		var url = "182821/69056359.html";
		var uri = "https://www.31xiaoshuo.com/182/182821/";
		var builder = new StringBuilder();
		for (int i = 0, j = 0, size = uri.length(), len = url.length();  j < len; i++) {
			if (i < size) {
				var cur = uri.charAt(i);
				builder.append(cur);
				var tar = url.charAt(j);
				if (cur == tar) {
					j++;
				} else {
					j = 0;
				}
			} else {
				builder.append(url.charAt(j++));
			}
		}
		System.out.println(builder);
	}
}