package com.lizp.validator.by;

import com.lizp.lang.Err;
import com.lizp.validator.constraints.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private Class<? extends Enum> enumClass;

    @Override
    public boolean isValid(Object validObj, ConstraintValidatorContext constraintValidatorContext) {
        //校验值类型short、int、String都有可能
        if (validObj == null || (validObj + "").isEmpty()) {
            return true;
        }
        try {
            Err.codeOf((Class) enumClass, validObj);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.value();
    }
}
