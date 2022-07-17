package com.jdy.angel.ebook.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Dale
 * @create 2022/7/16 1:22
 */
@Configuration("ebook.WebConfiguration")
public class WebConfiguration {


	@Data
	@Configuration
	@ConfigurationProperties(prefix = "angel.ebook")
	public static class BookConf {
		/**
		 * 书籍信息配置
		 */
		private Map<String, String> maps;

		private String root;
	}

	@Data
	@Configuration
	@ConfigurationProperties(prefix = "angel.ebook.article")
	public static class ArticleConf {
		/**
		 * 内容 id
		 */
		private String content;
	}

	@Data
	@Configuration
	@ConfigurationProperties(prefix = "angel.ebook.article.gene")
	public static class GeneConf {

		private String identify;

		private boolean zip;

	}
}
