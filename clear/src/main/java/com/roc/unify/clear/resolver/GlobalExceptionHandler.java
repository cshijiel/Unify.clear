package com.roc.unify.clear.resolver;

import com.roc.unify.clear.annotation.ExceptionHandler;
import com.roc.unify.clear.domain.exception.BizRuntimeException;
import com.roc.unify.clear.domain.result.ResultCodeEnum;
import com.roc.unify.clear.domain.result.ResultVO;

/**
 * 全局异常处理器 Demo
 *
 * @author roc
 */
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVO handleIllegalArgumentException(IllegalArgumentException e) {
        return ResultVO.commonBuilder(ResultCodeEnum.PARAMETER_ERROR)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(BizRuntimeException.class)
    public ResultVO handleBizRuntimeException(BizRuntimeException e) {
        return ResultVO.builder()
                .success(Boolean.FALSE)
                .code(e.getErrorCode())
                .message(e.getMessage())
                .build();
    }

    // other handle logic...

    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        return ResultVO.commonBuilder(ResultCodeEnum.SYSTEM_ERROR)
                .success(Boolean.FALSE)
                .message(e.getMessage())
                .build();
    }
}