package com.roc.unify.clear;

import com.roc.unify.clear.config.Configuration;
import com.roc.unify.clear.flow.ExceptionResolver;
import com.roc.unify.clear.flow.JsrValidator;
import com.roc.unify.clear.flow.LogPrinter;
import com.roc.unify.clear.util.RefUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;

import java.lang.reflect.Method;

/**
 * Let`s clear the wrong logic
 * todo:
 * 1. 扩展机制
 * 2. 日志打印格式确定
 *
 * @author roc
 */
@Slf4j
public class Unify {

    public static final String TRACE_KEY = Configuration.TRACE_KEY;

    /**
     * 统一异常处理入口
     *
     * @param pjp                    切点
     * @param returnType             返回类型
     * @param globalExceptionHandler 全局异常处理器，Method返回值均为returnType
     * @return 执行结果，有可能是异常结果
     * @throws Throwable 不能处理的异常
     */
    public static Object clear(ProceedingJoinPoint pjp, Class<?> returnType, Object globalExceptionHandler)
            throws Throwable {
        String traceId = Configuration.traceUtil.get();
        MDC.put(TRACE_KEY, traceId);
        Method classMethod = RefUtil.getClassMethod(pjp);

        // 类型检查
        if (!returnType.isAssignableFrom(classMethod.getReturnType())) {
            return pjp.proceed();
        }

        Object result;
        try {
            LogPrinter.printLog4InputParams(pjp);
            JsrValidator.validate(pjp);
            result = pjp.proceed();
        } catch (Throwable throwable) {
            result = ExceptionResolver.processException(pjp, throwable, returnType, globalExceptionHandler);
        } finally {
            MDC.clear();
        }
        LogPrinter.printLog4ReturnValues(pjp, result);
        return result;
    }
}