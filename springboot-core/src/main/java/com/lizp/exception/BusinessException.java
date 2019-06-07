package com.lizp.exception;


import com.lizp.lang.Err;


public class BusinessException extends RuntimeException {
    protected String code;
    protected Object[] args;

    protected BusinessException() {
        super();
    }

    /**
     * @param code           错误编码
     * @param args           错误消息参数，用于国际化时替换message中的变量
     * @param defaultMessage 默认消息
     * @param cause          异常原因
     */
    public BusinessException(String code, Object[] args, String defaultMessage, Throwable cause) {
        super(defaultMessage, cause);
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code, String defaultMessage, Throwable cause) {
        this(code, null, defaultMessage, cause);
    }

    public BusinessException(Err err, String message, Throwable cause) {
        this(err.getCode(), null, message, cause);
    }

    public BusinessException(Err err, Throwable cause) {
        this(err.getCode(), null, err.getMsg(), cause);
    }

    public BusinessException(int code, Object[] args, String defaultMessage, Throwable cause) {
        this(String.valueOf(code), args, defaultMessage, cause);
    }

    public BusinessException(int code, String defaultMessage, Throwable cause) {
        this(code, null, defaultMessage, cause);
    }

    public BusinessException(String code, Object[] args, String defaultMessage) {
        this(code, args, defaultMessage, null);
    }

    public BusinessException(Err err) {
        this(err.getCode(), null, err.getMsg(), null);
    }

    public BusinessException(Err err, String message) {
        this(err.getCode(), null, message, null);
    }

    public BusinessException(String code, String defaultMessage) {
        this(code, null, defaultMessage, null);
    }


    public BusinessException(int code, Object[] args, String defaultMessage) {
        this(String.valueOf(code), args, defaultMessage, null);
    }

    public BusinessException(int code, String defaultMessage) {
        this(code, null, defaultMessage, null);
    }

    public BusinessException(String code, Object[] args, Throwable cause) {
        this(code, args, null, cause);
    }

    public BusinessException(String code, Throwable cause) {
        this(code, null, null, cause);
    }

    public BusinessException(String code, Object[] args) {
        this(code, args, null, null);
    }

    public BusinessException(String code) {
        this(code, null, null, null);
    }

    public BusinessException(int code, Object[] args, Throwable cause) {
        this(String.valueOf(code), args, cause);
    }

    public BusinessException(int code, Throwable cause) {
        this(code, null, null, cause);
    }

    public BusinessException(int code, Object[] args) {
        this(String.valueOf(code), args, null, null);
    }

    public BusinessException(int code) {
        this(String.valueOf(code));
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": code=" + code + ", message=" + message) : (s + ": " + code);
    }
}
