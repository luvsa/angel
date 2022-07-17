package com.jdy.angel.core;

/**
 * @author Dale
 * @create 2022/7/16 12:01
 */
public interface Trigger<T> {

	void register(T item);

}
