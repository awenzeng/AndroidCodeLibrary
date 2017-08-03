package com.awen.codebase.annotition;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/7/31.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface UserParam {
    String name() default "";
    String phone() default "";
}
