package com.example.dcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dcloud.entity.Dictionary;
import com.example.dcloud.entity.Menu;
import com.example.dcloud.mapper.MenuMapper;
import com.example.dcloud.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fifteen
 * @since 2020-04-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public String pageList(int cur_page) {
        QueryWrapper<Menu> queryWrapper =  new QueryWrapper<>();
        queryWrapper
                .eq("is_deleted",0)
                .orderByAsc("id");
        Page<Menu> page = new Page<>(cur_page,10);  // 查询第1页，每页返回10条
        IPage<Menu> iPage = menuMapper.selectPage(page,queryWrapper);
        return JSON.toJSONString(iPage);
    }

    @Override
    public String queryList(String name,Integer page,Integer is_visible) {
        QueryWrapper<Menu> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0)
                .eq("is_visible",is_visible)
                .like("name",name);
//        queryWrapper.lambda().eq(Menu::getIsDeleted, 0).and(
//                queryWrapper1 -> queryWrapper1.eq(Menu::getIsVisible,is_visible)
//                        .like(Menu::getName,name));
        Page<Menu> page1 = new Page<>(page,10);  // 查询第1页，每页返回10条
        IPage<Menu> iPage = menuMapper.selectPage(page1,queryWrapper);
        return JSON.toJSONString(iPage);
    }
}
