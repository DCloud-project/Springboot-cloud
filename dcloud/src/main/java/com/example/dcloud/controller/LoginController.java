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
public class LoginController {
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
    public String register(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        JSONObject jsonObject1 = new JSONObject();
        if(userService.registerService(email,password)==1){
            jsonObject1.put("respCode","1");
            return jsonObject1.toString();
        }
        else{
            jsonObject1.put("respCode","账号已存在");
            return jsonObject1.toString();
        }
    }

    @PostMapping(value = "/loginByPassword")
    public String loginByPassword(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        JSONObject jsonObject1 = new JSONObject();
        if(userService.loginByPwd(email,password)==2){
            jsonObject1.put("respCode","1");
            jsonObject1.put("role",userService.selectRole(email));
            return jsonObject1.toString();
        }
        else{
            jsonObject1.put("respCode","账号或密码错误");
            jsonObject1.put("role","-1");
            return jsonObject1.toString();
        }
    }

    @PostMapping(value = "/loginByCode")
    public String loginByCode(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        JSONObject jsonObject1 = new JSONObject();
        if(userService.loginByCode(email)==1){
            jsonObject1.put("respCode","1");
            jsonObject1.put("role",userService.selectRole(email));
            return jsonObject1.toString();
        }
        else{
            jsonObject1.put("respCode","账号不存在");
            jsonObject1.put("role","-1");
            return jsonObject1.toString();
        }
    }
}
