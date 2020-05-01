package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
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
}
