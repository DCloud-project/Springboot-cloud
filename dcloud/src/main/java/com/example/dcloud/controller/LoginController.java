package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dcloud.annotation.NoToken;
import com.example.dcloud.entity.User;
import com.example.dcloud.service.RoleService;
import com.example.dcloud.service.UserService;
import com.example.dcloud.util.ResultUtil;
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

    @NoToken
    @PostMapping(value = "/loginByPassword")
    public String loginByPassword(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        JSONObject jsonObject1 = new JSONObject();
        if(userService.loginByPwd(email,password)==2){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email",email);
            User user = userService.getOne(queryWrapper);
            if(user.getIsDelete()==1){
                return ResultUtil.error("账号已被删除！");
            }
            String token = JWT.create().withAudience(user.getId() + "")
                    .sign(Algorithm.HMAC256(user.getPassword()));
            jsonObject1.put("respCode","1");
            int role_id = userService.selectRole(email);
            jsonObject1.put("role",role_id);
            jsonObject1.put("token",token);
            return jsonObject1.toString();
        }
        else{
            jsonObject1.put("respCode","账号或密码错误");
            jsonObject1.put("role","-1");
            return jsonObject1.toString();
        }
    }

    @NoToken
    @PostMapping(value = "/loginByCode")
    public String loginByCode(@RequestBody JSONObject jsonObject)
    {
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = (String) map.get("email");
        JSONObject jsonObject1 = new JSONObject();
        if(userService.loginByCode(email)==1){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email",email);
            User user = userService.getOne(queryWrapper);
            if(user.getIsDelete()==1){
                return ResultUtil.error("账号已被删除！");
            }
            String token = JWT.create().withAudience(user.getId() + "")
                    .sign(Algorithm.HMAC256(user.getPassword()));
            jsonObject1.put("respCode","1");
            int role_id = userService.selectRole(email);
            jsonObject1.put("role",role_id);
            jsonObject1.put("token",token);
            return jsonObject1.toString();
        }
        else{
            jsonObject1.put("respCode","账号不存在");
            jsonObject1.put("role","-1");
            return jsonObject1.toString();
        }
    }
}
