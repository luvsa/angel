package com.jdy.angel.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 *
 * @author Aglet
 * @create 2022/7/18 21:59
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {

    String value() default "";

    boolean capital() default false;
}
