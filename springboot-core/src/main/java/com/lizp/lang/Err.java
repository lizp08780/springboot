package com.lizp.lang;

import com.lizp.exception.BusinessException;

public interface Err {
    String getCode();

    String getMsg();

    default boolean equals2(Object code) {
        return this.getCode().equals(code + "");
    }

    default Integer getInt() {
        return Integer.parseInt(this.getCode());
    }

    static <E extends Enum<?> & Err> E codeOf(Class<E> enumClass, Object code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.equals2(code)) {
                return e;
            }
        }
        throw new BusinessException(CommonErrors.INVALID_PARAM, code + "不是" + enumClass + "的枚举值");
    }
}
