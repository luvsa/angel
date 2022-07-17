package com.jdy.angel.ebook.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdy.angel.core.CollectTrigger;
import com.jdy.angel.ebook.dto.ArticlePriority;
import com.jdy.angel.ebook.entity.Article;
import com.jdy.angel.ebook.mapper.ArticleMapper;
import com.jdy.angel.ebook.repository.ArticleRepository;
import org.luvsa.vary.Vary;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author Dale
 * @create 2022/7/16 10:22
 */
@Repository
public class ArticleRepositoryImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleRepository {

	private final Map<String, String> map = new HashMap<>();

	private final Lock lock = new ReentrantLock();

	private final CollectTrigger<Article> trigger;

	public ArticleRepositoryImpl() {
		this.trigger = new CollectTrigger<>(100, articles -> {
			if (saveBatch(articles)) {
				articles.clear();
			}
		});
	}

	@Override
	public ArticlePriority getPriority(String guid, String name) {
		var priority = adorn(name);
		//序列
		var sequence = getSequence(guid);
		if (sequence == null) {
			return new ArticlePriority(false, priority);
		}
		var c = sequence.charAt(priority);
		return new ArticlePriority(c == '1', priority);
	}

	@Override
	public void register(Article article) {
		var guid = article.getBook();
		var priority = article.getPriority();
		do {
			try {
				if (lock.tryLock(2, TimeUnit.SECONDS)) {
					var s = map.get(guid);
					var chars = s.toCharArray();
					if (s.length() > priority) {
						chars[priority] = '1';
						map.put(guid, new String(chars));
					} else {
						var array = new char[priority + 1];
						var index = chars.length;
						System.arraycopy(chars, 0, array, 0, index);
						Arrays.fill(array, index, priority - 1, '0');
						array[priority] = '1';
						map.put(guid, new String(array));
					}
					break;
				}
			} catch (InterruptedException e) {
				//
			} finally {
				lock.unlock();
			}
		} while (true);
		trigger.register(article);
	}

	private String getSequence(String guid) {
		do {
			try {
				if (lock.tryLock(2, TimeUnit.SECONDS)) {
					return getSequence0(guid);
				}
			} catch (InterruptedException e) {
				//
			} finally {
				lock.unlock();
			}
		} while (true);
	}

	private String getSequence0(String guid) {
		var s = map.get(guid);
		if (s == null) {
			var wrapper = new QueryWrapper<Article>();
			wrapper.eq("book", guid);
			wrapper.orderByAsc("priority");
			var articles = baseMapper.selectList(wrapper);
			if (articles.isEmpty()) {
				return null;
			}
			var size = articles.size();
			var last = articles.get(size - 1);
			var length = last.getPriority();
			var array = new int[length];
			for (var article : articles) {
				var index = article.getPriority();
				array[index] = 1;
			}
			var builder = new StringBuilder();
			for (int i : array) {
				builder.append(i);
			}
			s = builder.toString();
			map.put(guid, s);
		}
		return s;
	}

	private int adorn(String name) {
		var from = name.indexOf("第");
		if (from < 0) {
			return -1;
		}
		var to = name.indexOf("章", from);
		if (to < from) {
			return -1;
		}
		var num = name.substring(from + 1, to).strip();
		return Vary.change(num, int.class);
	}

	@PreDestroy
	public void destroy() {
		trigger.destroy();
	}
}
