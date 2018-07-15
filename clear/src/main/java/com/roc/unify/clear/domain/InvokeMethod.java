package com.roc.unify.clear.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author roc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvokeMethod {
    private Object proxy;
    private Object target;

    private Method method;
    private Object[] args;

    private Exception exception;
}
