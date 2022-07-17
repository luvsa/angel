package com.jdy.angel.ebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 电子书服务
 *
 * @author Dale
 * @create 2021/10/13 16:47
 */
@SpringBootApplication
@MapperScan(value = {"com.jdy.angel.mapper.sys", "com.jdy.angel.ebook.mapper"})
public class EbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbookApplication.class, args);
	}

}
