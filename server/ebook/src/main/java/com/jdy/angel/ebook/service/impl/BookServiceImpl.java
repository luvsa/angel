package com.jdy.angel.ebook.service.impl;

import com.jdy.angel.ebook.core.Node;
import com.jdy.angel.ebook.core.Parser;
import com.jdy.angel.ebook.core.net.Request;
import com.jdy.angel.ebook.entity.Book;
import com.jdy.angel.ebook.repository.BookRepository;
import com.jdy.angel.ebook.security.WebConfiguration.BookConf;
import com.jdy.angel.ebook.service.ArticleService;
import com.jdy.angel.ebook.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.luvsa.lang.Strings;
import org.luvsa.reflect.Reflects;
import org.luvsa.vary.Vary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Aglet
 * @create 2022/7/3 21:29
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

	private final Map<String, BiConsumer<Object, String>> local = new HashMap<>();

	private final ArticleService service;

	private final BookRepository repository;




	public BookServiceImpl(BookConf mapper, ArticleService service, BookRepository repository) {
		this.service = service;
		this.repository = repository;
		var maps = mapper.getMaps();
		Reflects.doWithLocalFields(Book.class, field -> {
			var fieldName = field.getName();
			var key = maps.get(fieldName);
			if (key == null) {
				return;
			}
			var set = Reflects.findMethod(Book.class, method -> {
				var methodName = method.getName();
				if (!methodName.startsWith("set")) {
					return false;
				}
				var cur = Strings.uncapitalize(methodName.substring(3));
				return Objects.equals(cur, fieldName);
			});
			local.put(key, (o, s) -> {
				var types = set.getParameterTypes();
				var args = new Object[types.length];
				for (int i = 0; i < types.length; i++) {
					args[i] = Vary.change(s, types[i]);
				}
				try {
					Reflects.invokeMethod(set, o, args);
				} catch (Exception e) {
					log.error("放射方式设置[" + field + "]值出错！", e);
				}
			});
		});
	}

	@Override
	public void start(Request domain) throws Exception {
		Parser.remote(domain, node -> {
			var head = node.getNode("head");
			var book = analyse(head.getChildren());
			var origin = node.get("origin");
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

				var url = merge(origin, s);
				next(book.getGuid(), nod.getChildren().get(0).toString(), url);
			}
		});
	}

	private String merge(String uri, String url) {
		var builder = new StringBuilder();
		for (int i = 0, j = 0, size = uri.length(), len = url.length(); j < len; i++) {
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
		return builder.toString();
	}

	private void next(String guid, String name, String url) {
		service.start(guid, name, () -> url);
	}

	private Book analyse(List<Node> children) {
		var book = new Book();
		for (var child : children) {
			var label = child.getLabel();
			var property = label.get("property");
			var content = label.get("content");
			if (property == null || property.isBlank() || content == null || content.isBlank()) {
				continue;
			}
			var consumer = local.get(property);
			if (consumer != null){
				consumer.accept(book, content);
			}
		}
		return repository.saveOrGet(book);
	}
}
