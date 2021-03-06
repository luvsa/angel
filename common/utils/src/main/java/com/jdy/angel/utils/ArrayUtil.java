package com.jdy.angel.utils;

import com.jdy.angel.CharPredicate;
import com.jdy.angel.Combiner;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Aglet
 * @create 2022/7/5 15:08
 */
public final class ArrayUtil {

    private ArrayUtil() {
    }

    /**
     * 判断 数组 中是否含有满足条件的元素
     *
     * @param predicate 条件
     * @param args      数组
     * @param <T>       数组的数据类型
     * @return 数组中只要有一个满足条件，则返回 ture
     */
    @SafeVarargs
    public static <T> boolean has(Predicate<T> predicate, T... args) {
        return CollectUtil.has(predicate, List.of(args));
    }

    /**
     * 判断 数组 中的元素是否都满足条件
     *
     * @param predicate 条件
     * @param args      数组
     * @param <T>       数组的数据类型
     * @return 数组中只要有一个不满足条件，则返回 false
     */
    @SafeVarargs
    public static <T> boolean have(Predicate<T> predicate, T... args) {
        return CollectUtil.have( predicate, List.of(args));
    }

    public static boolean has(char cur, char... chars) {
        return has(value -> value == cur, chars);
    }

    public static boolean have(char cur, char... chars) {
        return have(value -> value == cur, chars);
    }

    /**
     * 判断 数组 中是否含有满足条件的元素
     *
     * @param predicate 条件
     * @param args      数组
     * @return 数组中只要有一个满足条件，则返回 ture
     */
    public static boolean has(CharPredicate predicate, char... args) {
        return has(true, predicate, args);
    }

    /**
     * 判断 数组 中的元素是否都满足条件
     *
     * @param predicate 条件
     * @param args      数组
     * @return 数组中只要有一个不满足条件，则返回 false
     */
    public static boolean have(CharPredicate predicate, char... args) {
        return has(false, predicate, args);
    }

    private static boolean has(boolean flag, CharPredicate predicate, char...args){
        for (char item : args) {
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


    public static void descartes(Consumer<int[]> consumer,  int...array){
        var size = array.length;
        descartes(array, new Combiner<>() {
            private int index;
            private final int[] array = new int[size];
            @Override
            public void onNext(Integer item) {
                array[index] =  item;
                if (item < size){
                    index++;
                }
            }
            @Override
            public void onComplete() {
                consumer.accept(array);
            }

            @Override
            public void reset(int index) {
                if (index < 0){
                    index = 0;
                }
                this.index = index;
            }
        }, 0);
    }

    private static void descartes(int[] array, Combiner<Integer> consumer, int index){
        if (index < array.length){
            var size = array[index];
            for (int i = 0; i < size; i++) {
                consumer.onNext(i);
                descartes(array, consumer, index + 1);
            }
        }  else {
            consumer.onComplete();
        }
        consumer.reset(index  - 1);
    }
}
