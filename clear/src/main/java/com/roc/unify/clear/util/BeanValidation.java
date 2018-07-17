package com.roc.unify.clear.util;

import com.roc.unify.clear.domain.exception.BizRuntimeException;
import com.roc.unify.clear.domain.result.ResultCodeEnum;
import com.roc.unify.clear.flow.JsrValidator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 验证，执行Bean Validation标准
 *
 * @author roc
 */
public class BeanValidation {

    private static final Validator VALIDATOR = JsrValidator.DEFAULT_VALIDATOR;

    /**
     * 检查，并抛出异常，主要是为了校验，执行Bean Validation标准，其他简单校验可以使用
     *
     * @param waitForCheck 等待被检查的对象
     * @throws BizRuntimeException 运行时异常，包括CODE & MSG
     * @see Assert
     */
    public static void checkAndThrow(Object waitForCheck) throws BizRuntimeException {
        Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(waitForCheck);
        if (!CollectionUtils.isEmpty(violations)) {
            String violationMsg = JsrValidator.doParseConstraintViolation(violations);
            throw new BizRuntimeException(ResultCodeEnum.PARAMETER_ERROR, violationMsg);
        }
    }

    /**
     * 检查，并抛出组装错误信息
     *
     * @param waitForCheck 等待被检查的对象
     * @return 错误信息
     */
    public static String check(Object waitForCheck) {
        String violationMsg = null;
        Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(waitForCheck);
        if (!CollectionUtils.isEmpty(violations)) {
            violationMsg = JsrValidator.doParseConstraintViolation(violations);
        }
        return violationMsg;
    }
}