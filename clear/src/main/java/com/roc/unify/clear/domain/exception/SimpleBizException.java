package com.roc.unify.clear.domain.exception;

import com.roc.unify.clear.domain.result.ResultCodeInterface;

/**
 * 简单业务异常，无堆栈信息
 * 另，JDK默认对异常有优化，参见OmitStackTraceInFastThrow
 *
 * @author roc
 */
public class SimpleBizException extends BizRuntimeException {
    public SimpleBizException(ResultCodeInterface error) {
        super(error);
    }

    public SimpleBizException(ResultCodeInterface error, String message) {
        super(error, message);
    }

    public SimpleBizException(String code, String message) {
        super(code, message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}