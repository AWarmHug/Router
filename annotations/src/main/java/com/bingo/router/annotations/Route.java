package com.bingo.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：warm
 * 时间：2019-07-20 14:50
 * 描述：
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Route {
    String value() default "";

    Class<?> pathClass() default Object.class;

    String[] interceptors() default {};
}
