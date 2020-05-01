package com.example.dcloud.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dcloud.entity.Role;
import com.example.dcloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fifteen
 * @since 2020-04-12
 */
@Controller
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping(value = "/roleList")
    public List<Role> roleList(@RequestBody JSONObject jsonObject) {
       QueryWrapper<Role> wrapper = new QueryWrapper<>();

       return roleService.list();
    }
}

