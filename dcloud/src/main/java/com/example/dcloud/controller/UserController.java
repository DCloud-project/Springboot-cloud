package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dcloud.service.MailService;
import com.example.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @PostMapping(value = "/sendCode")
    public String sendCode(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String checkCode = String.valueOf(new Random().nextInt(899999)+100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendMail(email,"到云注册验证码",message);
            return checkCode;
        }catch (Exception e){
            return "";
        }
    }

    @PostMapping(value = "/register")
    public int register(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return userService.registerService(email,password);
    }

    @PostMapping(value = "/loginByPassword")
    public int loginByPassword(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return userService.loginByPwd(email,password);
    }

    @PostMapping(value = "/loginByCode")
    public int loginByCode(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        return userService.loginByCode(email);
    }
}
