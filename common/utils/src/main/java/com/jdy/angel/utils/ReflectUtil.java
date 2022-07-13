package com.jdy.angel.utils;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 反射工具
 *
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
     * 查找指定 Field 相关的方法， 设置 {@code field 值}的方法
     * <p>
     * 假设 Field 的名称为： name
     *
     * <ul>
     *     <li>1. <pre>{@code public void setName(String name) {}}</pre></li>
     *     <li>2. <pre>{@code public void name(String name) {}}</pre></li>
     * </ul>
     *
     * @param field 指定 Field
     * @return 方法
     */
    public static Method getSetMethod(Field field) {
        var name = field.getName();
        var method = "set" + StringUtil.capitalize(name);
        var aClass = field.getDeclaringClass();
        return getSetMethod(aClass, method, field.getType());
    }

    public static Method getSetMethod(Class<?> source, String name, Class<?>... types) {
        try {
            return source.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> type, int size) {
        return (T[]) Array.newInstance(type, size);
    }
}
