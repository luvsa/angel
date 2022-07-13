package com.jdy.angel;

/**
 * @author Aglet
 * @create 2022/7/12 14:19
 */
public class Combiners {
    public static Combiner<Character> ofChar(){
        return new Combiner<>() {

            @Override
            public void onNext(Character item) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void reset(int index) {
            }

        };
    }
}
