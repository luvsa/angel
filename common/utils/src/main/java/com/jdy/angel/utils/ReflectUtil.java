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

    /**
     * 获取 调用目标 类方法的上层 类的 class 对象，
     * 实现 {@link jdk.internal.reflect.Reflection#getCallerClass() 获取调用者的Class} 功能
     *
     * @param clazz 被调用的 class 对象
     * @return 调用者的class对象
     */
    public static Class<?> getCallerClass(Class<?> clazz) {
        var found = false;
        var name = clazz.getName();
        var elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement item : elements) {
            var className = item.getClassName();
            if (Objects.equals(className, name)) {
                found = true;
                continue;
            }
            if (found) {
                try {
                    return Class.forName(className);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new IllegalArgumentException("无法找到调用 " + clazz + " 的类对象！");
    }

    /**
     * 查找指定 Field 相关的方法
     *
     * @param prefix 前缀
     * @param field  指定 Field
     * @return 方法
     */
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
