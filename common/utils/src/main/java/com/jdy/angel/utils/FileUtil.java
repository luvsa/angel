package com.jdy.angel.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
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
            readResource(name, builder::append);
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException("读取文件：" + name + "失败！");
        }
    }

    public static void readResource(String name, Consumer<String> consumer) {
        read(readResourceAsStream(name), consumer);
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

    public static void read(URL url, Consumer<String> consumer) {
        try {
            var uri = url.toURI();
            var file = new File(uri);
            read(file, consumer);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
            // 加上换行
            consumer.accept(s);
        } while (true);
    }

    public static void scanClass(Consumer<Class<?>> consumer) {
        scanClass(getCallerClass(), consumer);
    }

    public static void scanClass(Class<?> clazz, Consumer<Class<?>> consumer) {
        scan(clazz, (from, path) -> {
            var to = path.getNameCount();
            var sub = path.subpath(from, to);
            var s = sub.toString();
            var name = s.replace("\\", ".");
            try {
                var index = name.lastIndexOf(".");
                var className = name.substring(0, index);
                var clazz1 = Class.forName(className);
                consumer.accept(clazz1);
            } catch (ClassNotFoundException e) {
                System.err.println(name);
            }
        });
    }

    public static void scan(Class<?> clazz, BiConsumer<Integer, Path> consumer) {
        var domain = clazz.getProtectionDomain();
        var source = domain.getCodeSource();
        var location = source.getLocation();
        var file = new File(location.getPath());
        var root = file.getPath();
        var path = Path.of(root);
        var from = path.getNameCount();
        var replace = clazz.getPackageName().replace(".", File.separator);
        try {
            Files.walkFileTree(path.resolve(replace), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    consumer.accept(from, file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private interface Reader {

        String readLine() throws IOException;

    }
}
