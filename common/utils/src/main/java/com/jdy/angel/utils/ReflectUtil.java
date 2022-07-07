package com.jdy.angel.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/5 14:54
 */
public final class ReflectUtil {

    private ReflectUtil() {
    }

    public static Class<?> getCallerClass(Class<?> clazz) {
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

    public static Method search(String prefix, Field field) {
        var name = field.getName();
        var method = prefix + StringUtil.capitalize(name);
        var aClass = field.getDeclaringClass();
        try {
            return aClass.getMethod(method, field.getType());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
