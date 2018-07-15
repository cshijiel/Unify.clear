package com.roc.unify.clear.util;

import org.junit.Assert;
import org.junit.Test;

public class TraceUtilTest {
    @Test
    public void getTraceId() {
        String traceId = TraceUtil.getTraceId();
        Assert.assertNotNull(traceId);
    }
}
