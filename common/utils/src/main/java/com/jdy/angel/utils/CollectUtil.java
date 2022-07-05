package com.jdy.angel.utils;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Aglet
 * @create 2022/7/5 15:20
 */
public class CollectUtil {


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

    public static <T> boolean has(Predicate<T> predicate, List<T> args) {
        return has0(true, predicate, args);
    }


    public static <T> boolean have(Predicate<T> predicate, List<T> args) {
        return has0(false, predicate, args);
    }
}
