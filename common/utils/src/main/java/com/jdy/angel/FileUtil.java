package com.jdy.angel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        //  Reflection.getCallerClass() 的替代方案
        var flag = false;
        var clazz = FileUtil.class;
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
        read(path, s -> list.add(function.apply(s)));
        return list;
    }

    public static String readString(Path path){
        var builder = new StringBuilder();
        read(path, builder::append);
        return builder.toString();
    }

    public static String readString(File path){
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
            read0(access::readLine, consumer);
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
            consumer.accept(s);
        } while (true);
    }

    private interface Reader {

        String readLine() throws IOException;

    }
}
