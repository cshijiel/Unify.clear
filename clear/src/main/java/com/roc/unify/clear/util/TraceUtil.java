package com.roc.unify.clear.util;

import java.util.UUID;

/**
 * trace id 获取工具
 * TODO 注册式
 *
 * @author roc
 */
public class TraceUtil {
    public static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}