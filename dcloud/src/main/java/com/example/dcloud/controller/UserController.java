package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dcloud.entity.User;
import com.example.dcloud.service.UserService;
import com.example.dcloud.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String userList(
            @RequestParam(value="page",required = false)Integer page,
            @RequestParam(value="state",required = false)String state,
            @RequestParam(value="name",required = false)String name
    ){
        return userService.userList(state,name,page);
//        int length = list.size();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("totalCount",totalCount);
//        jsonArray.add(jsonObject2);
//        for(int i = 0;i<length;i++){
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("name",list.get(i).get("name"));
//            jsonObject1.put("sex",list.get(i).get("sex"));
//            jsonObject1.put("email",list.get(i).get("email"));
//            jsonObject1.put("roleId",list.get(i).get("roleId"));
//            jsonObject1.put("state",list.get(i).get("state"));
//            jsonObject1.put("schoolName",list.get(i).get("schoolId"));
//            jsonArray.add(jsonObject1);
//        }
//        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String addUser(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String name = map.get("name").toString();
        int sex = Integer.parseInt(map.get("sex").toString());
        String email = map.get("email").toString();
        String password = "123456";
        int roleId = Integer.parseInt(map.get("roleId").toString());
        JSONObject jsonObject1 = new JSONObject();
        if(userService.addUser(name,sex,email,password,roleId)==1){
            jsonObject1.put("respCode","1");
            return  jsonObject1.toString();
        }else{
            jsonObject1.put("respCode","账号已存在");
            return  jsonObject1.toString();
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public String updateUserByAdmin(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String name = map.get("name").toString();
        int sex = Integer.parseInt(map.get("sex").toString());
        String email = map.get("email").toString();
        int roleId = Integer.parseInt(map.get("roleId").toString());
        userService.updateUserByAdmin(name,sex,roleId,email);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("respCode","1");
        return jsonObject1.toString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam(value = "del_list") List list){
        for(int i=0;i<list.size();i++){
            String email = list.get(i).toString();
            userService.deleteUser(email);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("respCode","1");
        return jsonObject1.toString();
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public String changeUserState(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = map.get("email").toString();
        userService.changeUserStateService(email);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("respCode","1");
        return jsonObject1.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        String email = map.get("email").toString();
        String newPassword = map.get("newpassword1").toString();
        String repeatNewPassword = map.get("newpassword2").toString();
        String oldPassword = map.get("oldpassword").toString();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        User user = userService.getOne(queryWrapper);
        String password = user.getPassword();
        int id = user.getId();
        if(oldPassword.equals(password)){
            if(newPassword.equals(repeatNewPassword)){
                User user1 = new User();
                user1.setId(id);
                user1.setPassword(newPassword);
                userService.updateById(user1);
                return ResultUtil.success();
            }
            else{
                return ResultUtil.error("两次输入的密码不一致");
            }
        }
        else{
            return ResultUtil.error("原密码错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/resetPassword",method = RequestMethod.PUT)
    public String resetPassword(@RequestParam(value="email",required = false)String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        User user = new User();
        user.setPassword("123456");
        try{
            userService.update(user,queryWrapper);
            return ResultUtil.success();
        } catch (Exception e){
            return ResultUtil.error("重置失败");
        }
    }


}
