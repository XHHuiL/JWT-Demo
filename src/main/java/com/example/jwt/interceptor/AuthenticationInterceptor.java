package com.example.jwt.interceptor;

import com.example.jwt.annotation.TokenRequired;
import com.example.jwt.exception.AuthenticationException;
import com.example.jwt.util.JWTUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod))
            return true;
        TokenRequired annotation = ((HandlerMethod) handler).getMethod().getAnnotation(TokenRequired.class);
        // 如果存在@TokenRequired注解，那么便验证token
        if (annotation != null) {
            String token = request.getHeader("Authentication");
            if (token == null)
                throw new AuthenticationException("authentication fail", "no header named 'authentication'");
            if (!JWTUtil.authentication(token))
                throw new AuthenticationException("authentication fail", "invalid token");
            return true;
        }
        return true;
    }
}