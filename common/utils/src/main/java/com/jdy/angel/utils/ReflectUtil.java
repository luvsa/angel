package com.jdy.angel.utils;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/5 14:54
 */
public final class ReflectUtil {

    private ReflectUtil() {
    }

    static Class<?> getCallerClass(Class<?> clazz) {
        //  Reflection.getCallerClass() 的替代方案
        var flag = false;
        var name = clazz.getName();
        var elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement item : elements) {
            var className = item.getClassName();
            if (Objects.equals(className, name)) {
                flag = true;
                continue;
            }
            if (flag) {
                try {
                    return Class.forName(className);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException();
    }

}
