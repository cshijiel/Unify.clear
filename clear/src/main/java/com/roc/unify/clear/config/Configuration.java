package com.roc.unify.clear.config;

import com.roc.unify.clear.util.TraceUtil;

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

}
