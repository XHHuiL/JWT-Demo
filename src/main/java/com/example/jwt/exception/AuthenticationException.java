package com.example.jwt.exception;

/**
 * 自定义的验证异常，继承自RuntimeException（非检查异常）
 */
public class AuthenticationException extends RuntimeException {

    private String code;

    private String message;

    public AuthenticationException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}