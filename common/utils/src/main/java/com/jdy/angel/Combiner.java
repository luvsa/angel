package com.jdy.angel;

/**
 * @author Aglet
 * @create 2022/7/12 9:55
 */
public interface Combiner<T> {

    void onNext(T item);

    void onComplete();

    void reset(int index);
}
