package com.roc.unify.clear.aspect;


import com.roc.unify.clear.Unify;
import com.roc.unify.clear.domain.ResultVO;
import com.roc.unify.clear.resolver.GlobalExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect Demo
 *
 * @author roc
 */

public class HsfProviderAspect {

    private static final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Pointcut("@within(com.roc.unify.clear.annotation.Api)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object testPointcutAround(ProceedingJoinPoint pjp) throws Throwable {
        return Unify.clear(pjp, ResultVO.class, globalExceptionHandler);
    }
}