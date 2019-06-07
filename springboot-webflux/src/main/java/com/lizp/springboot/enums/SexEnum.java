package com.lizp.springboot.enums;

import com.lizp.lang.Err;

public enum SexEnum implements Err {
    UNKNOW("0", "未知"),
    MAN("1", "男"),
    WOMAN("2", "女");

    private String code;
    private String msg;

    SexEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "SexEnum{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }


}
