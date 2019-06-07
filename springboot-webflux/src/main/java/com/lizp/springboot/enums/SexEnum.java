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
    public Err[] getValues() {
        return SexEnum.values();
    }

    //必须实现该方法，以便@EnumValue可以使用
    public static String getMsg(String code) {
        //选择任意枚举值调用即可，父类已实现
        return SexEnum.UNKNOW.msgOf(code);
    }

    @Override
    public String toString() {
        return "SexEnum{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }


}
