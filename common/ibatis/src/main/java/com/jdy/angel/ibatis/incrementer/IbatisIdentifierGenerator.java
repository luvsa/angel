package com.jdy.angel.ibatis.incrementer;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 *数据ID 生成器
 * @author Dale
 * @create 2022/7/16 15:12
 */
@Slf4j
@Primary
@Component
@ConditionalOnClass(IdentifierGenerator.class)
public class IbatisIdentifierGenerator implements IdentifierGenerator {
	private final Sequence sequence;

	private final AtomicLong atomicLong = new AtomicLong(0);

	public IbatisIdentifierGenerator() {
		log.info("加载数据ID 生成器...");
		this.sequence = new Sequence(null);
	}

	@Override
	public Number nextId(Object entity) {
		return sequence.nextId();
	}

	@Override
	public String nextUUID(Object entity) {

		var prefix = String.valueOf(sequence.nextId());
		var num = String.valueOf(atomicLong.incrementAndGet());

		int length = num.length();
		var len = 32 - prefix.length();
		if (length > len) {
			atomicLong.set(0);
			return nextUUID(entity);
		}

		return prefix + "0".repeat(len - length) + num;
	}
}
