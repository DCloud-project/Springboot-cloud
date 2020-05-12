package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dcloud.entity.Role;
import com.example.dcloud.service.RoleService;
import com.example.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @PostMapping(value = "/loginByPassword")
    public String loginByPassword(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        JSONObject jsonObject1 = new JSONObject();
        if(userService.loginByPwd(email,password)==2){
            jsonObject1.put("respCode","1");
            int role_id = userService.selectRole(email);
            Role role = roleService.getById(role_id);
            String power_id = role.getPowerId();
            String[] power = power_id.split(",");
            jsonObject1.put("role",role_id);
            jsonObject1.put("power",power);
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
            int role_id = userService.selectRole(email);
            Role role = roleService.getById(role_id);
            String power_id = role.getPowerId();
            String[] power = power_id.split(",");
            jsonObject1.put("role",role_id);
            jsonObject1.put("power",power);
            return jsonObject1.toString();
        }
        else{
            jsonObject1.put("respCode","账号不存在");
            jsonObject1.put("role","-1");
            return jsonObject1.toString();
        }
    }
}
