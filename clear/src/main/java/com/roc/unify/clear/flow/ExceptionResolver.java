package com.roc.unify.clear.flow;

import com.roc.unify.clear.resolver.ExceptionHandlerMethodResolver;
import com.roc.unify.clear.util.RefUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 将异常转化为Result对象
 *
 * @author roc
 */
@Slf4j
public class ExceptionResolver {
    public static Object processException(ProceedingJoinPoint pjp, Throwable throwable, Class<?> returnType,
                                          Object globalExceptionHandler) {
        Method classMethod = RefUtil.getClassMethod(pjp);
        // for inner exception handle
        Object target = pjp.getTarget();
        log.error(RefUtil.getMethodFullName(classMethod) + ": ", throwable);

        // 防御式做法
        if (!(throwable instanceof Exception)) {
            throwable = new RuntimeException(throwable);
        }
        Exception exception = ((Exception) throwable);

        // inner exception handler
        Object exceptionHandler = target;
        ExceptionHandlerMethodResolver resolver = new ExceptionHandlerMethodResolver(exceptionHandler.getClass());
        Method resolveMethod = resolver.resolveMethod(exception);
        if (resolveMethod == null) {
            // global exception handler
            exceptionHandler = globalExceptionHandler;
            resolver = new ExceptionHandlerMethodResolver(exceptionHandler.getClass());
            resolveMethod = resolver.resolveMethod(exception);
        }

        Assert.isTrue(returnType.isAssignableFrom(classMethod.getReturnType()), "Type Must Be SAME");
        Object invoke = null;
        try {
            invoke = resolveMethod.invoke(exceptionHandler, throwable);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }
}