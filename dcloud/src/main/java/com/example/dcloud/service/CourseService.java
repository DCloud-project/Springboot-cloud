package com.example.dcloud.service;

import com.example.dcloud.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fifteen
 * @since 2020-05-30
 */
public interface CourseService extends IService<Course> {
    String jsonArraySort(String jsonArrStr,String sortKey);
    String searchArray(String jsonArr,String search);
    String searchLesson(String jsonArr,String search);
    String getRank(String jsonArr,String email);
}
