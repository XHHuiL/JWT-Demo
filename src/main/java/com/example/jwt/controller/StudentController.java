package com.example.jwt.controller;


import com.example.jwt.annotation.TokenRequired;
import com.example.jwt.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {

    @GetMapping(value = "/student/sign")
    public String sign(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        String token;
        if ((token = JWTUtil.sign(username, password, true, false)) != null)
            return token;
        return "sign fail";
    }

    @GetMapping(value = "/student/authentication")
    @TokenRequired
    public String authentication() {
        return "authentication success";
    }

}