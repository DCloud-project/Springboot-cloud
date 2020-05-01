package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dcloud.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@CrossOrigin
@Controller
@RequestMapping("/sendCode")
public class MailController {

    @Autowired
    MailService mailService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
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
}
