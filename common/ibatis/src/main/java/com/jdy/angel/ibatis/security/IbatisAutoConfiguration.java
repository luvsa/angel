package com.jdy.angel.ibatis.security;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 自动装配
 *
 * @author Dale
 * @create 2022/7/16 15:12
 */
@Slf4j
@Configuration
@ConditionalOnClass(MybatisPlusInterceptor.class)
@MapperScan("com.jdy.angle.common.data.mapper")
public class IbatisAutoConfiguration {

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		log.info("分页处理器 ...");
		var interceptor = new MybatisPlusInterceptor();

		// 分页插件
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

		//乐观锁
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

		//防止恶意全表更新或删除
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

		return interceptor;
	}
}
