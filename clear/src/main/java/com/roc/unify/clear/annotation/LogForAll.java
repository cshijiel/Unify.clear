package com.roc.unify.clear.annotation;

import java.lang.annotation.*;

/**
 * 日志打印
 *
 * @author roc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@LogForParams
@LogForResult
public @interface LogForAll {
    boolean value() default false;
}