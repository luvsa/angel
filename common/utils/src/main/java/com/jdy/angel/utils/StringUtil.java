package com.jdy.angel.utils;

import org.luvsa.lang.Strings;

/**
 * @author Aglet
 * @create 2022/7/6 17:20
 */
public final class StringUtil {

	private StringUtil() {
	}

	public static String uncapitalize(String str) {
		return Strings.uncapitalize(str);
	}

	public static String capitalize(String str) {
		return Strings.capitalize(str);
	}
}
