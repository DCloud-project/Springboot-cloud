package com.example.dcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dcloud.service.MailService;
import com.example.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @PostMapping(value = "/userList")
    public String userList(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        int currentPage = Integer.parseInt(map.get("page").toString());
        int totalCount = userService.selectUserNum()-1;
        int offset = (currentPage-1)*10;
        List<Map> list = userService.selectUser(offset);
        int length = list.size();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("totalCount",totalCount);
        jsonArray.add(jsonObject2);
        for(int i = 0;i<length;i++){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("name",list.get(i).get("name"));
            jsonObject1.put("sex",list.get(i).get("sex"));
            jsonObject1.put("email",list.get(i).get("email"));
            jsonObject1.put("roleId",list.get(i).get("roleId"));
            jsonObject1.put("state",list.get(i).get("state"));
            jsonObject1.put("schoolName",list.get(i).get("schoolId"));
            jsonArray.add(jsonObject1);
        }
        return jsonArray.toString();
    }

    @PostMapping(value = "/addUser")
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

    @PostMapping(value = "/updateUserByAdmin")
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

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestBody List<Map> list){
        for(int i=0;i<list.size();i++){
            String email = list.get(i).get("email").toString();
            userService.deleteUser(email);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("respCode","1");
        return jsonObject1.toString();
    }

    @PostMapping(value = "/searchUser")
    public String searchUser(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String strState = map.get("state").toString();
        int state;
        if(strState.equals("")==true){
            state = -1;
        }
        else{
            state = Integer.parseInt(strState);
        }
        String name = "%" + map.get("name").toString() + "%";
        int currentPage = Integer.parseInt(map.get("page").toString());
        int offset = (currentPage-1)*10;
        int totalCount = userService.searchUserNum(state,name,offset);
        List<Map> list = userService.searchUser(state,name,offset);
        int length = list.size();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("totalCount",totalCount);
        jsonArray.add(jsonObject2);
        for(int i = 0;i<length;i++){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("name",list.get(i).get("name"));
            jsonObject1.put("sex",list.get(i).get("sex"));
            jsonObject1.put("email",list.get(i).get("email"));
            jsonObject1.put("roleId",list.get(i).get("roleId"));
            jsonObject1.put("state",list.get(i).get("state"));
            jsonObject1.put("schoolName",list.get(i).get("schoolId"));
            jsonArray.add(jsonObject1);
        }
        return jsonArray.toString();
    }

    @PostMapping(value = "/changeUserState")
    public String changeUserState(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject,Map.class);
        String email = map.get("email").toString();
        userService.changeUserStateService(email);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("respCode","1");
        return jsonObject1.toString();
    }
}
