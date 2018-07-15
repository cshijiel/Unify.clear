package com.roc.unify.clear.annotation;


import java.lang.annotation.*;

/**
 * API的GlobalExceptionHandler标记
 *
 * @author roc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiAdvice {
}