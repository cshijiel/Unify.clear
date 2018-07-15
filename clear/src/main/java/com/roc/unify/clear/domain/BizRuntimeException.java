package com.roc.unify.clear.domain;

/**
 * 业务运行时异常
 *
 * @author roc
 */
public class BizRuntimeException extends RuntimeException {
    private String errorCode;

    public BizRuntimeException(ResultCodeInterface error) {
        super(error.message());
        this.errorCode = error.code();
    }

    public BizRuntimeException(ResultCodeInterface error, String message) {
        super(message);
        this.errorCode = error.code();
    }

    public BizRuntimeException(String code, String message) {
        super(message);
        this.errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }
}