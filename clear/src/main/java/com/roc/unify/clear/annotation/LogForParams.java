package com.roc.unify.clear.annotation;

import java.lang.annotation.*;

/**
 * 入参参数打印
 *
 * @author roc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogForParams {
    boolean value() default false;
}