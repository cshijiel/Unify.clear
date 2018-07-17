package com.roc.unify.clear.domain.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果对象, Demo
 *
 * @author roc
 */
@Data
@Builder
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -4448696578582628909L;

    private String traceId;
    private Boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> ResultVOBuilder<T> successBuilder(T data) {
        return ResultVO.<T>commonBuilder(ResultCodeEnum.SUCCESS)
                .success(Boolean.TRUE)
                .data(data);
    }

    public static <T> ResultVOBuilder<T> commonBuilder(ResultCodeInterface resultCode) {
        return ResultVO.<T>builder()
                .code(resultCode.code())
                .message(resultCode.message());
    }
}