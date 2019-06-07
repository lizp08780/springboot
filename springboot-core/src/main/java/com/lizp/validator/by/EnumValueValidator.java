package com.lizp.validator.by;

import com.lizp.exception.BusinessException;
import com.lizp.lang.CommonErrors;
import com.lizp.validator.constraints.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private Class<? extends Enum> enumClass;
    private static final String METHOD_NAME = "getMsg";

    @Override
    public boolean isValid(Object validObj, ConstraintValidatorContext constraintValidatorContext) {
        //校验值类型short、int、String都有可能
        if (validObj == null || (validObj + "").isEmpty()) {
            return true;
        }
        Method declareMethod;
        try {
            declareMethod = enumClass.getDeclaredMethod(METHOD_NAME, String.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        try {
            declareMethod.invoke(null, validObj + "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.value();
        try {
            enumClass.getDeclaredMethod(METHOD_NAME, String.class);
        } catch (NoSuchMethodException e) {
            throw new BusinessException(CommonErrors.INVALID_PARAM, enumClass + "枚举没有实现" + METHOD_NAME + "方法");
        }
    }
}
