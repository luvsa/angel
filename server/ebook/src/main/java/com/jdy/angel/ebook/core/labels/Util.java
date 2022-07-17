package com.jdy.angel.ebook.core.labels;

import com.jdy.angel.utils.FileUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author Aglet
 * @create 2022/7/5 19:04
 */
class Util {

    private final static Map<String, Supplier<Label>> cache = new ConcurrentHashMap<>();

    static {
        FileUtil.scanClass(clazz -> {
            var sign = clazz.getAnnotation(Sign.class);
            if (sign == null) {
                return;
            }
            if (!Label.class.isAssignableFrom(clazz)) {
                return;
            }

            try {
                var constructor = clazz.getConstructor();
                var supplier = new Supplier<Label>() {
                    @Override
                    public Label get() {
                        try {
                            return (Label) constructor.newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                for (String s : sign.value()) {
                    cache.put(s, supplier);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    private Util() {
    }

    public static Label create(String name, String property) {
        var key = name.startsWith("/") ? name.substring(1) : name;
        var supplier = cache.get(key);
        var label = supplier == null ? new Mark() : supplier.get();
        label.setProperty(property.strip());
        label.setName(name);
        return label;
    }
}
