package com.jdy.angel.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 集合工具
 *
 * @author Aglet
 * @create 2022/7/5 15:20
 */
public final class CollectUtil {

    private CollectUtil() {
    }

    /**
     * 判断 元素中 {true:是否存在} | {false:全部都} 满足指定条件
     *
     * @param flag      true:是否存在； false:全部都
     * @param predicate 条件
     * @param list      元素集合
     * @param <T>       元素数据类型
     * @return 检查结果
     */
    private static <T> boolean has0(boolean flag, Predicate<T> predicate, List<T> list) {
        for (T item : list) {
            var test = predicate.test(item);
            if (test) {
                if (flag) {
                    // has 有一个满足条件， 直接返回 true
                    return true;
                }
                // hava 需要所有的元素都满足条件
                continue;
            }

            if (flag) {
                // has， 看下一个是否满足条件
                continue;
            }

            // hava 不满足条件， 直接返回 false
            return false;
        }
        return !flag;
    }

    /**
     * 指定集合中是否存在满足条件的元素
     *
     * @param predicate 条件
     * @param args      元素集合
     * @param <T>       元素数据类型
     * @return 只要集合中存在一个元素满足条件： 返回 true： 否则返回： false
     */
    public static <T> boolean has(Predicate<T> predicate, List<T> args) {
        return has0(true, predicate, args);
    }

    /**
     * 指定集合中所有元素是否都满足条件
     *
     * @param predicate 条件
     * @param args      元素集合
     * @param <T>       元素数据类型
     * @return 只要集合中存在一个元素不满足条件， 返回 false： 否则返回 true
     */
    public static <T> boolean have(Predicate<T> predicate, List<T> args) {
        return has0(false, predicate, args);
    }


    public static <T> void descartes(Iterator<? extends Collection<T>> iterator, Consumer<List<T>> consumer) {





//        descartes(iterator, 0, new Combiner<>() {
//
//            private List<T> cache = new ArrayList<>();
//
//            @Override
//            public void onNext(T item) {
//                cache.add(item);
//            }
//
//            @Override
//            public void onComplete() {
//                consumer.accept(cache);
//            }
//
//            @Override
//            public void reset(int index) {
//                if (index < 1) {
//                    cache = new ArrayList<>();
//                } else {
//                    var list = cache;
//                    cache = new ArrayList<>();
//                    for (int i = 0; i < index; i++) {
//                        cache.add(list.get(i));
//                    }
//                }
//            }
//        });
    }
//
//    /**
//     * 集合笛卡尔积算法
//     *
//     * @param iterator 集合源
//     * @param consumer 消费器
//     * @param <T>      笛卡尔积对象数据类型
//     */
//    public static <T> void descartes(Iterator<? extends Collection<T>> iterator, Combiner<Integer> consumer) {
//        descartes(iterator, 0, consumer);
//    }
//
//    private static <T> void descartes(Iterator<? extends Collection<T>> iterator, int index, Combiner<Integer> composer) {
//        if (iterator.hasNext()) {
//            var list = iterator.next();
//            var size = list.size();
//            for (var item : list) {
//                composer.onNext(item);
//                descartes(iterator, index + 1, composer);
//            }
//        } else {
//            composer.onComplete();
//        }
//        composer.reset(index - 1);
//    }
}
