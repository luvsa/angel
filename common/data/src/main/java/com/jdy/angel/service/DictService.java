package com.jdy.angel.service;

import org.luvsa.vary.Vary;

/**
 * @author Dale
 * @create 2022/7/16 13:41
 */
public interface DictService {

//	static <T> T adjust(Dictionary dictionary, String value, Class<T> type) {
//
//	}

	default <T> T search(String name, String label, Class<T> clazz) {
		var search = search(name, label);
		if (search == null) {
			return null;
		}
		return Vary.change(search, clazz);
	}

	/**
	 * @param name  字典名称
	 * @param label 字段名称
	 * @return 字段值
	 */
	String search(String name, String label);

}
