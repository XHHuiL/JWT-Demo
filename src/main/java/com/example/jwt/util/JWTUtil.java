package com.example.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    // token过期时间，单位ms
    private static final long EXPIRE_TIME = 30 * 1000;
    // 使用指令 node -e "console.log(require('crypto').randomBytes(32).toString('hex'));" 生成的密钥
    private static final String TOKEN_SECRET = "fd942a9a893497d25ea2d0e377d60cea4217b7f43ac0f54eabeea5aa0f638002";

    // 签发token
    public static String sign(String username, String password, boolean canQuery, boolean canUpdate) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            // 附带username，password等信息，生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .withClaim("query", true)
                    .withClaim("update", false)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    // 验证token
    public static boolean authentication(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 获取用户名
    public static String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }

    // 获取密码
    public static String getPassword(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("password").asString();
    }

    // 判断是否有查询权限
    public static boolean canQuery(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("canQuery").asBoolean();
    }

    // 判断是否有更新权限
    public static boolean canUpdate(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("canUpdate").asBoolean();
    }
}