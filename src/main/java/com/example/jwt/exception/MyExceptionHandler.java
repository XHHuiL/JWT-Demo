package com.example.jwt.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandler {

    // 处理所有的未知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map unknownExceptionHandler(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "unknown exception");
        map.put("message", "server internal error");
        return map;
    }

    // 处理自定义的认证异常
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public Map authenticationExceptionHandler(AuthenticationException e) {
        Map<String, String> map = new HashMap<>();
        map.put("code", e.getCode());
        map.put("message", e.getMessage());
        return map;
    }

}