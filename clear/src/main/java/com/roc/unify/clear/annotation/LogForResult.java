package com.roc.unify.clear.annotation;

import java.lang.annotation.*;

/**
 * 返回值打印
 *
 * @author roc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogForResult {
    boolean value() default false;
}