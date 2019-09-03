package com.warm.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：warm
 * 时间：2019-07-20 15:05
 * 描述：
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.CLASS)
public @interface Parameter {
    String name() default "";
}
