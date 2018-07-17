package com.roc.unify.clear.domain.result;

/**
 * error interface for Enum & Exception
 *
 * @author roc
 */
public interface ResultCodeInterface {
    /**
     * 错误码
     *
     * @return 错误码
     */
    String code();

    /**
     * 错误具体信息
     *
     * @return 错误具体信息
     */
    String message();
}