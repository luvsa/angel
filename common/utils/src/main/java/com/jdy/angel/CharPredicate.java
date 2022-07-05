package com.jdy.angel;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/5 15:29
 */
public interface CharPredicate {

    boolean test(char item);

    default CharPredicate and(CharPredicate other) {
        Objects.requireNonNull(other);
        return (value) -> test(value) && other.test(value);
    }

    default CharPredicate negate() {
        return (value) -> !test(value);
    }

    default CharPredicate or(CharPredicate other) {
        Objects.requireNonNull(other);
        return (value) -> test(value) || other.test(value);
    }
}
