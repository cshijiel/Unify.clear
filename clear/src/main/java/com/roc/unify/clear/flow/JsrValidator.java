package com.roc.unify.clear.flow;

import com.roc.unify.clear.domain.BizRuntimeException;
import com.roc.unify.clear.domain.ResultCodeEnum;
import com.roc.unify.clear.util.RefUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.validator.internal.engine.ValidatorContextImpl;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * JSR 380
 *
 * @author roc
 */
public class JsrValidator {

    public static final ExecutableValidator VALIDATOR;
    public static final Validator DEFAULT_VALIDATOR;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        ValidatorContextImpl validatorContext = (ValidatorContextImpl) validatorFactory.usingContext();
        validatorContext.allowOverridingMethodAlterParameterConstraint(true);
        DEFAULT_VALIDATOR = validatorContext.getValidator();
        VALIDATOR = DEFAULT_VALIDATOR.forExecutables();
    }


    /**
     * 1. 从局部到整体,校验范围配置：参数、方法、类、全局
     * 2. 校验primate类型和复杂类型
     * 3. 有问题抛出去
     *
     * @param pjp 切入点
     */
    public static void validate(ProceedingJoinPoint pjp) {
        Object target = pjp.getTarget();
        Object[] args = pjp.getArgs();
        Method realMethod = RefUtil.getClassMethod(pjp);

        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validateParameters(target, realMethod, args);
        processConstraintViolations(constraintViolations, realMethod);
    }

    private static void processConstraintViolations(Set<ConstraintViolation<Object>> constraintViolations, Method realMethod) {
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            String errorMessage = parseConstraintViolation(constraintViolations, realMethod);
            // 这里可以更通用些, 但是统一错误码也没什么坏处
            throw new BizRuntimeException(ResultCodeEnum.PARAMETER_ERROR, errorMessage);
        }
    }

    private static String parseConstraintViolation(Set<ConstraintViolation<Object>> constraintViolations, Method realMethod) {
        String methodFullName = RefUtil.getMethodFullName(realMethod);
        return methodFullName + " $$ " + "<< " + doParseConstraintViolation(constraintViolations) + " >>";
    }

    public static String doParseConstraintViolation(Set<ConstraintViolation<Object>> constraintViolations) {
        List<String> kvs = new ArrayList<>();
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String innerStr = String.valueOf(constraintViolation.getPropertyPath()) +
                    " " + constraintViolation.getMessage();
            kvs.add(innerStr);
        }
        return StringUtils.join(kvs, ", ");
    }
}