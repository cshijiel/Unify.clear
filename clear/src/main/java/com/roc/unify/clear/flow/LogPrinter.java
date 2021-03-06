package com.roc.unify.clear.flow;

import com.roc.unify.clear.annotation.LogForAll;
import com.roc.unify.clear.annotation.LogForParams;
import com.roc.unify.clear.annotation.LogForResult;
import com.roc.unify.clear.domain.InvokeMethod;
import com.roc.unify.clear.util.ClearParameterNameProvider;
import com.roc.unify.clear.util.RefUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.ParameterNameProvider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印入参出参日志
 *
 * @author roc
 */
@Slf4j
public class LogPrinter {

    /**
     * Global config
     * 目前是白名单机制，需要手动设置打印入参出参
     */
    public static Boolean logForInputParams;
    public static Boolean logForResult;
    public static boolean logForAll = false;

    private static ParameterNameProvider discoverer = new ClearParameterNameProvider();

    private static String safeToString(Object o) {
        return ToStringBuilder.reflectionToString(o);
    }

    private static boolean checkLogConfigForInputParams(Method method) {
        LogForParams logForParams = AnnotationUtils.getAnnotation(method, LogForParams.class);
        if (logForParams != null) {
            return logForParams.value();
        }
        LogForAll logForAll = AnnotationUtils.getAnnotation(method, LogForAll.class);
        if (logForAll != null) {
            return logForAll.value();
        }
        logForParams = AnnotationUtils.getAnnotation(method.getDeclaringClass(), LogForParams.class);
        if (logForParams != null) {
            return logForParams.value();
        }
        logForAll = AnnotationUtils.getAnnotation(method.getDeclaringClass(), LogForAll.class);
        if (logForAll != null) {
            return logForAll.value();
        }
        Boolean logForInputParams = LogPrinter.logForInputParams;
        if (logForInputParams != null) {
            return logForInputParams;
        }
        return LogPrinter.logForAll;
    }

    private static boolean checkLogConfigForResult(Method method) {
        LogForResult logForResult = AnnotationUtils.getAnnotation(method, LogForResult.class);
        if (logForResult != null) {
            return logForResult.value();
        }
        LogForAll logForAll = AnnotationUtils.getAnnotation(method, LogForAll.class);
        if (logForAll != null) {
            return logForAll.value();
        }
        logForResult = AnnotationUtils.getAnnotation(method.getDeclaringClass(), LogForResult.class);
        if (logForResult != null) {
            return logForResult.value();
        }
        logForAll = AnnotationUtils.getAnnotation(method.getDeclaringClass(), LogForAll.class);
        if (logForAll != null) {
            return logForAll.value();
        }
        Boolean logForResultCheck = LogPrinter.logForResult;
        if (logForResultCheck != null) {
            return logForResultCheck;
        }
        return LogPrinter.logForAll;
    }

    public void printLog4InputParams(InvokeMethod invokeMethod) {
        Method classMethod = invokeMethod.getMethod();
        boolean needPrintLog = LogPrinter.checkLogConfigForInputParams(classMethod);
        if (!needPrintLog) {
            return;
        }

        List<String> parameterNames = discoverer.getParameterNames(classMethod);
        Object[] args = invokeMethod.getArgs();

        StringBuilder builder = new StringBuilder();


        List<String> kvs = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String parameterName = parameterNames.get(i);
            String value = safeToString(args[i]);
            kvs.add(parameterName + "=" + value);
        }
        String join = StringUtils.join(kvs, "|");
        builder.append(join);
        log.error(builder.toString());
    }

    public void printLog4ReturnValues(InvokeMethod invokeMethod) {
        Method classMethod = invokeMethod.getMethod();
        boolean needPrintLog = LogPrinter.checkLogConfigForResult(classMethod);
        if (!needPrintLog) {
            return;
        }

        String methodFullName = RefUtil.getMethodFullName(classMethod);
        log.info(methodFullName + ": result = {}", invokeMethod.getResult());
    }

    public void printLog4Exceptions(InvokeMethod invokeMethod) {
        Method classMethod = invokeMethod.getMethod();
        String methodFullName = RefUtil.getMethodFullName(classMethod);
        log.error(methodFullName + ": ", invokeMethod.getThrowable());
    }
}