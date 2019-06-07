package com.lizp.lang;

public enum CommonErrors implements Err {
    /**
     * 处理成功
     */
    SUCCESS("0000", "成功"),
    /**
     * AppID不合法
     */
    INVALID_APPID("0001", "AppID不合法"),
    /**
     * 签名验证失败
     */
    SIGNATURE_VERIFY_FAIL("0002", "签名验证失败"),
    /**
     * 没有权限
     */
    UNAUTHORIZED("0003", "没有权限"),
    /**
     * 参数错误
     */
    INVALID_PARAM("0004", "参数错误"),
    /**
     * 非法token或已失效
     */
    TOKEN_VERIFY_FAIL("0005", "非法token或已失效"),
    /**
     * 服务不存在
     */
    SERVICE_NOT_EXIST("0006", "服务不存在"),
    /**
     * 服务暂不可用
     */
    SERVICE_UNAVAILABLE("0007", "服务暂不可用"),
    /**
     * 未知错误
     */
    UNDEFINED("9999", "系统错误");

    /**
     * 编码
     */
    private String code;
    /**
     * 消息
     */
    private String msg;

    CommonErrors(String code, String msg) {
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
}
