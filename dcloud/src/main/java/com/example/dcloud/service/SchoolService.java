package com.example.dcloud.service;

import com.example.dcloud.entity.School;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fifteen
 * @since 2020-05-11
 */
public interface SchoolService extends IService<School> {
    String queryList(Integer page,String school_name,String academy_name);
    String getChildList(Integer page,Integer id);
    String getAll();
}
