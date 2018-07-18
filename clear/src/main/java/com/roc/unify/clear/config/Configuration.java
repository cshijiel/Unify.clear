package com.roc.unify.clear.config;

import com.roc.unify.clear.domain.InvokeMethod;
import com.roc.unify.clear.flow.JsrValidator;
import com.roc.unify.clear.flow.LogPrinter;
import com.roc.unify.clear.util.TraceUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 配置
 *
 * @author roc
 */
public class Configuration {

    /**
     * trace Util，可更换
     */
    public static Supplier<String> traceUtil = TraceUtil::getTraceId;

    public static String TRACE_KEY = "traceId";


    public static class LogConfig {
        public static Boolean logForInputParams;
        public static Boolean logForResult;
        public static boolean logForAll = false;

        public static Consumer<InvokeMethod> log4InputParams = new LogPrinter()::printLog4InputParams;
        public static Consumer<InvokeMethod> log4ReturnValues = new LogPrinter()::printLog4ReturnValues;
        public static Consumer<InvokeMethod> log4Exceptions = new LogPrinter()::printLog4Exceptions;
    }

    public static class ValidatorConfig {
        public static Consumer<InvokeMethod> jsrValidator = new JsrValidator()::validate;
    }


}
