package com.example.dcloud.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dcloud.entity.CourseManage;
import com.example.dcloud.entity.User;
import com.example.dcloud.service.CourseManageService;
import com.example.dcloud.service.UserService;
import com.example.dcloud.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fifteen
 * @since 2020-06-18
 */
@CrossOrigin
@Controller
@RequestMapping("/courseManage")
public class CourseManageController {
    @Autowired
    private CourseManageService courseManageService;
    @Autowired
    private UserService userService;
    //获取 查询
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam(value="name",required = false)String name,
                      @RequestParam(value = "page",required = false)Integer page){
        if(name != null){//搜索课程
            return courseManageService.pageListforQuery(name,page);
        }else if(page != null){
            return courseManageService.pageList(page);
        }else{//获取所有课程名称
            return courseManageService.getCourseName();
//            List<CourseManage> list = courseManageService.list();
//            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < list.size(); i++) {
//                JSONObject result = new JSONObject();
//                result.put("id",list.get(i).getId().toString());
//                result.put("name",list.get(i).getName().toString());
//                jsonArray.add(result);
//            }
//            return JSON.toJSONString(jsonArray);
        }
    }

    //增加
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveOne(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        //名称不能重复
        QueryWrapper<CourseManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",map.get("name").toString());
        int count = courseManageService.count(queryWrapper);
        if(count > 0){
            return ResultUtil.error("课程名称不允许重复！");
        }
        CourseManage courseManage = new CourseManage();
        courseManage.setName(map.get("name").toString());
        QueryWrapper<User> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("email",map.get("email").toString());
        User user = userService.getOne(queryWrapper1);
        courseManage.setCreatedName(user.getName());
        LocalDateTime time = LocalDateTime.now();
        courseManage.setCreatedTime(time);
        courseManage.setType(parseInt(map.get("type").toString()));
        if(map.containsKey("description")){
            courseManage.setDescription(map.get("description").toString());
        }
        courseManage.setIsDeleted(0);
        courseManageService.save(courseManage);
        return ResultUtil.success();
    }

    //删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String delMenu(@RequestParam(value = "ids") List list) {
        for(int i = 0; i < list.size(); i++){
            CourseManage courseManage = new CourseManage();
            courseManage.setId(parseLong(list.get(i).toString()));
            courseManage.setIsDeleted(1);
            courseManageService.updateById(courseManage);
        }
        return ResultUtil.success();
    }
    //修改
    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public String update(@RequestBody JSONObject jsonObject){
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        CourseManage courseManage = new CourseManage();
        courseManage.setId(parseLong(map.get("id").toString()));
        courseManage.setName(map.get("name").toString());
        QueryWrapper<User> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("email",map.get("email").toString());
        User user = userService.getOne(queryWrapper1);
        courseManage.setCreatedName(user.getName());
        LocalDateTime time = LocalDateTime.now();
        courseManage.setCreatedTime(time);
        courseManage.setType(parseInt(map.get("type").toString()));
        if(map.containsKey("description")){
            courseManage.setDescription(map.get("description").toString());
        }
        courseManage.setIsDeleted(0);
        courseManageService.updateById(courseManage);
        return ResultUtil.success();
    }


}

