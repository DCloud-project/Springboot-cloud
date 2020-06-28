package com.example.dcloud.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dcloud.entity.*;
import com.example.dcloud.service.*;
import com.example.dcloud.util.DistanceUtil;
import com.example.dcloud.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fifteen
 * @since 2020-06-18
 */
@Controller
@CrossOrigin
@RequestMapping("/attendenceResult")
public class AttendenceResultController {
    @Autowired
    private AttendenceService attendenceService;
    @Autowired
    private AttendenceResultService attendenceResultService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseStudentService courseStudentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SystemManageService systemManageService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String addResult(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        String code = map.get("code").toString();
        String email = map.get("student_email").toString();
        String studentLocal = map.get("local").toString();
        System.out.println(map);
        QueryWrapper<Attendence> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code)
                .eq("is_delete", 0);

        if (attendenceService.count(queryWrapper) == 0)
            return ResultUtil.error("当前无签到");
        else {
            Attendence attendence = attendenceService.getOne(queryWrapper);
            int attendId = attendence.getId();
            QueryWrapper<AttendenceResult> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("student_email", email)
                    .eq("attend_id", attendId);
            if (attendenceResultService.count(queryWrapper1) != 0)
                return ResultUtil.error("请勿重复签到！");

            String teacherLocal = attendence.getLocal();
            Double distance = DistanceUtil.getDistanceMeter(teacherLocal,studentLocal);

            //获取设定的距离参数
            List<SystemManage> list = systemManageService.list();
            int systemDistance = list.get(0).getAttendDistance();
            if(distance>systemDistance){
                return ResultUtil.error("签到位置过远！");
            }

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            AttendenceResult attendenceResult = new AttendenceResult();
            attendenceResult.setAttendTime(sdf.format(d));
            attendenceResult.setCode(code);
            attendenceResult.setStudentEmail(email);
            attendenceResult.setAttendId(attendId);
            attendenceResult.setIsDelete(0);
            attendenceResultService.save(attendenceResult);
            return sdf.format(d);
        }
    }

    //某学生在某课程的签到历史记录
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public String historyAttendenceResult(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        String code = map.get("code").toString();
        String email = map.get("student_email").toString();
        QueryWrapper<Attendence> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code)
                .eq("is_delete", 1)
                .orderByDesc("id");
        //该课程所有的签到
        List<Attendence> list = attendenceService.list(queryWrapper);
        int k = 0;//该生的签到次数
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            String startTime = list.get(i).getStartTime();
            int attendId = list.get(i).getId();
            QueryWrapper<AttendenceResult> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("attend_id", attendId)
                    .eq("student_email", email);
            int count = attendenceResultService.count(queryWrapper1);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("time", startTime);

            if(count==0){
                jsonObject1.put("type", "缺勤");
            }else{
                AttendenceResult attendenceResult = attendenceResultService.getOne(queryWrapper1);
                int flag = attendenceResult.getIsDelete();
                if (flag == 2)
                    jsonObject1.put("type", "缺勤");
                else if(flag==0){
                    jsonObject1.put("type", "已签到");
                    k++;
                }
                else
                    jsonObject1.put("type", "请假");
            }
            jsonArray.add(jsonObject1);
        }
        String strPer;
        if(list.size()==0)
            strPer = "";
        else{
            int per = k * 100/ list.size() ;
            strPer = per + "%";
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("per", strPer);
        jsonArray.add(jsonObject1);
        return jsonArray.toString();
    }

    //签到结果
    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public String attendenceResult(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        String code = map.get("code").toString();
        String attendId = map.get("attend_id").toString();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        Course course = courseService.getOne(queryWrapper);
        long courseId = course.getId();
        //查询该课程包含的学生
        QueryWrapper<CourseStudent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId)
                .eq("is_delete", 0);
        List<CourseStudent> list = courseStudentService.list(queryWrapper1);
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        int k1 = 0, k2 = 0;
        for (int i = 0; i < list.size(); i++) {
            String email = list.get(i).getStudentEmail();
            QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("email", email);
            User user = userService.getOne(queryWrapper2);
            String name = user.getName();
            String sno = user.getSno();
            QueryWrapper<AttendenceResult> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("attend_id", attendId)
                    .eq("student_email", email);
            int count = attendenceResultService.count(queryWrapper3);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("name", name);
            jsonObject1.put("sno", sno);
            jsonObject1.put("email", email);
            if (count == 0) {
                jsonObject1.put("type", "缺勤");
                jsonArray1.add(jsonObject1);
                k1++;
            } else {
                AttendenceResult attendenceResult = attendenceResultService.getOne(queryWrapper3);
                if (attendenceResult.getIsDelete() == 0) {
                    jsonObject1.put("type", "已签到");
                    jsonArray2.add(jsonObject1);
                    k2++;
                } else if(attendenceResult.getIsDelete() == 1) {
                    jsonObject1.put("type", "请假");
                    jsonArray1.add(jsonObject1);
                    k1++;
                } else{
                    jsonObject1.put("type", "缺勤");
                    jsonArray1.add(jsonObject1);
                    k1++;
                }
            }
        }
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("total",k1);
        jsonArray1.add(jsonObject2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("total",k2);
        jsonArray2.add(jsonObject3);
        jsonArray.add(jsonArray1);
        jsonArray.add(jsonArray2);
        return jsonArray.toString();
    }

    //修改结果
    @ResponseBody
    @RequestMapping(value = "/change",method = RequestMethod.PUT)
    public String changeAttendenceResult(@RequestBody List<Map> list) {
        for(int i=0;i<list.size();i++){
            int attendId = Integer.parseInt(list.get(i).get("attend_id").toString());
            String email = list.get(i).get("student_email").toString();
            String code = list.get(i).get("code").toString();
            int type = Integer.parseInt(list.get(i).get("type").toString());
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

            QueryWrapper<AttendenceResult> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_email",email)
                    .eq("attend_id",attendId);
            int count = attendenceResultService.count(queryWrapper);
            if(count==0){
                AttendenceResult attendenceResult = new AttendenceResult();
                attendenceResult.setAttendTime(sdf.format(d));
                attendenceResult.setCode(code);
                attendenceResult.setStudentEmail(email);
                attendenceResult.setAttendId(attendId);
                attendenceResult.setIsDelete(type);
                attendenceResultService.save(attendenceResult);

                QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("email",email);
                User user = userService.getOne(queryWrapper1);
                int exp;
                if(type==0)
                    exp = user.getExp()+2;
                else
                    exp = user.getExp()+1;
                user.setExp(exp);
                userService.update(user,queryWrapper1);
            }
            else{
                AttendenceResult attendenceResult1 = attendenceResultService.getOne(queryWrapper);
                int flag = attendenceResult1.getIsDelete();

                AttendenceResult attendenceResult = new AttendenceResult();
                attendenceResult.setAttendTime(sdf.format(d));
                attendenceResult.setIsDelete(type);
                attendenceResultService.update(attendenceResult,queryWrapper);
                QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("email",email);
                User user = userService.getOne(queryWrapper1);
                int exp;
                if(type==2&&flag==0)
                    exp = user.getExp()-2;
                else if(type==2&&flag==1)
                    exp = user.getExp()-1;
                else if(type==1&&flag==0)
                    exp = user.getExp()-1;
                else if(type==1&&flag==2)
                    exp = user.getExp()+1;
                else if(type==0&&flag==1)
                    exp = user.getExp()+1;
                else
                    exp = user.getExp()+2;
                user.setExp(exp);
                userService.update(user,queryWrapper1);
            }
        }
        return ResultUtil.success();
    }
}

