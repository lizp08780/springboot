package com.lizp.lang;

import com.lizp.exception.BusinessException;

public interface Err {
    String getCode();

    String getMsg();

    default boolean equals2(Object obj) {
        return this.getCode().equals(obj + "");
    }

    default Integer getInt() {
        return Integer.parseInt(this.getCode());
    }

    Err[] getValues();

    //这里实现主要逻辑，简化枚举的实现
    default String msgOf(String code) {
        for (Err c : this.getValues()) {
            if (c.getCode().equals(code)) {
                return c.getMsg();
            }
        }
        throw new BusinessException(CommonErrors.INVALID_PARAM, "不存在枚举值:" + code);
    }
}
