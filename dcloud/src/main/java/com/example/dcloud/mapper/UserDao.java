package com.example.dcloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("SELECT email FROM user WHERE email = #{email}")
    String checkEmail(String account);  //查询账号
    @Insert("INSERT INTO user (name,nickname,image,sno,sex,telphone,email,password,birth,roleId,exp,state,schoolId," +
            "powerId,education,isDelete) VALUES('0','0','0','0','0','0',#{email},#{password},'0','0','0','0'," +
            "'0','0','0','0')")  //插入新账号
    void addUser(String email,String password);
    @Select("SELECT password FROM user WHERE email = #{email}")
    String checkPassword(String account);  //查询密码
}