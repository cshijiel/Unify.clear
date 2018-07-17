package com.roc.unify.clear.domain.result;

/**
 * 此枚举定义如果不合适或定义缺失，可自行实现ResultCodeInterface
 *
 * @author roc
 */
public enum ResultCodeEnum implements ResultCodeInterface {
    /**
     * 各种状态码
     */
    SUCCESS ("A00000", "成功"),
    /**
     * for JSR 303规范，最新Bean Validation为JSR 380，参见
     * http://download.oracle.com/otndocs/jcp/bean_validation-2_0_0-final-spec/
     */
    PARAMETER_ERROR ("Q00303", "请求参数有误"),
    AUTHORIZATION_ERROR ("Q00401", "权限不足"),
    RESOURCE_NOT_FOUND ("Q00404", "资源未找到"),
    TIME_OUT ("Q00408", "请求超时"),
    SYSTEM_ERROR ("Q00500", "系统错误"),

    //
    ;

    private String code;
    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}