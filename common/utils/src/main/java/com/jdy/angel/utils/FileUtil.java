package com.jdy.angel.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/5 13:28
 */
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * 读取文件，以流形式返回
     *
     * @param name 文件名称
     * @return 流
     */
    public static InputStream readResourceAsStream(String name) {
        var clazz = getCallerClass();
        var loader = clazz.getClassLoader();
        return loader.getResourceAsStream(name);
    }

    /**
     * 读取资源文件数据
     *
     * @param name 文件名称
     * @return 文件内容
     */
    public static String readResourceAsString(String name) {
        var builder = new StringBuilder();
        try {
            read(readResourceAsStream(name), builder::append);
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException("读取文件：" + name + "失败！");
        }
    }

    /**
     * 获取调用 FileUtil 工具的方法所在类的 Class 对象
     *
     * @return Class
     */
    private static Class<?> getCallerClass() {
        return ReflectUtil.getCallerClass(FileUtil.class);
    }

    /**
     * 遍历文件流
     *
     * @param stream   文件流
     * @param consumer 消费器
     */
    public static void read(InputStream stream, Consumer<String> consumer) {
        if (stream == null) {
            return;
        }
        try (var input = new InputStreamReader(stream); var reader = new BufferedReader(input)) {
            read(reader, consumer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> List<R> readAsList(Path path, Function<String, R> function) {
        var list = new ArrayList<R>();
        read(path, s -> {
            var item = function.apply(s);
            if (item != null) {
                list.add(item);
            }
        });
        return list;
    }

    public static String readString(Path path) {
        var builder = new StringBuilder();
        read(path, builder::append);
        return builder.toString();
    }

    public static String readString(File path) {
        var builder = new StringBuilder();
        read(path, builder::append);
        return builder.toString();
    }

    public static void read(Path path, Consumer<String> consumer) {
        try (var reader = Files.newBufferedReader(path)) {
            read(reader, consumer);
        } catch (IOException e) {
            throw new RuntimeException("Read path[" + path + "] failure!", e);
        }
    }

    public static void read(File file, Consumer<String> consumer) {
        try (var access = new RandomAccessFile(file, "r")) {
            read0(access::readLine, s -> {
                // 处理中文乱码问题
                var bytes = s.getBytes(StandardCharsets.ISO_8859_1);
                var current = new String(bytes, StandardCharsets.UTF_8);
                consumer.accept(current);
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Read file[" + file + "] failure!", e);
        }
    }

    public static void read(BufferedReader reader, Consumer<String> consumer) throws IOException {
        read0(reader::readLine, consumer);
    }

    private static void read0(Reader reader, Consumer<String> consumer) throws IOException {
        if (reader == null) {
            return;
        }
        do {
            var s = reader.readLine();
            if (s == null) {
                break;
            }
            // 加上换行
            consumer.accept(s + '\n');
        } while (true);
    }

    private interface Reader {

        String readLine() throws IOException;

    }
}
