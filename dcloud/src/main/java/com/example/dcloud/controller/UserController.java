package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dcloud.service.UserService;
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
}
