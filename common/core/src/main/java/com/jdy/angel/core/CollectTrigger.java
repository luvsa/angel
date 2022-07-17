package com.jdy.angel.core;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Dale
 * @create 2022/7/16 12:02
 */
@Slf4j
public class CollectTrigger<T> implements Trigger<T> {
	/**
	 * 数据收集器
	 */
	private final Collection<T> collection = new ArrayList<>();

	/**
	 * 多线程下数据操作的原子性
	 */
	private final AtomicBoolean atomic = new AtomicBoolean(false);

	/**
	 * 触发规则， 即触发数据处理的规则
	 */
	private final Predicate<T> predicate;
	/**
	 * 数据处理器
	 */
	private final Consumer<Collection<T>> consumer;

	public CollectTrigger(int maxSize, Consumer<Collection<T>> consumer) {
		this.predicate = t -> collection.size() > maxSize;
		this.consumer = consumer;
	}

	public CollectTrigger(Predicate<T> predicate, Consumer<Collection<T>> consumer) {
		this.predicate = predicate;
		this.consumer = consumer;
	}


	@Override
	public void register(T item) {
		while (atomic.get()) {
			log.info("等待........");
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				log.error("等待超时", e);
			}
		}

		//添加到数据集合
		collection.add(item);
		if (predicate.test(item) && atomic.compareAndSet(false, true)) {
			// cas 成 true， 使得其他线程处于 等待状态
			consumer.accept(collection);
			atomic.set(false);
		}
	}

	/**
	 * 系统 关闭之前， 处理剩下的数据
	 */
	public void destroy() {
		if (!collection.isEmpty()) {
			consumer.accept(collection);
		}
	}

	/**
	 * 主动 触发处理剩余数据
	 */
	public void check() {
		if (collection.isEmpty()) {
			return;
		}
		if (atomic.compareAndSet(false, true)) {
			consumer.accept(collection);
			atomic.set(false);
		}
	}
}
