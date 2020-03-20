package com.example.dcloud.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT roleId FROM user WHERE email = #{email}")
    int selRole(String account);  //查询角色

    @Select("SELECT name,sex,email,roleId,state,schoolId FROM user WHERE isDelete = 0 AND roleId != 2 LIMIT #{offset},10")
    List<Map>selUser(int offset);  //查询用户列表

    @Insert("INSERT INTO user (name,nickname,image,sno,sex,telphone,email,password,birth,roleId,exp,state,schoolId," +
            "powerId,education,isDelete) VALUES(#{name},'0','0','0',#{sex},'0',#{email},#{password},'0',#{roleId},'0','0'," +
            "'0','0','0','0')")  //管理员新增用户
    void addUserByAdmin(String name,int sex,String email,String password,int roleId);

    @Update("UPDATE user SET name = #{name},sex = #{sex},roleId = #{roleId} WHERE email = #{email}")
    void updUserByAdmin(String name,int sex,int roleId,String email);

    @Update("UPDATE user SET isDelete = 1 WHERE email = #{email}")
    void delUser(String email);

    @Select("SELECT COUNT(*) FROM user where isDelete = 0")
    int selUserNum();  //查询用户数

    @Select("SELECT name,sex,email,roleId,state,schoolId FROM user WHERE isDelete = 0 AND roleId != 2 AND state = #{state} LIMIT #{offset},10")
    List<Map>selUserByState(int state,int offset);  //根据状态查询用户

    @Select("SELECT name,sex,email,roleId,state,schoolId FROM user WHERE isDelete = 0 AND roleId != 2 AND name LIKE #{text} LIMIT #{offset},10")
    List<Map>selUserByName(@Param("text")String text,int offset);  //根据用户名查询用户

    @Select("SELECT name,sex,email,roleId,state,schoolId FROM user WHERE isDelete = 0 AND roleId != 2 AND name LIKE #{text} AND state = #{state} LIMIT #{offset},10")
    List<Map>selUserByNameAndState(@Param("text")String text,int state,int offset);  //根据用户名查询用户

    @Select("SELECT COUNT(*) FROM user WHERE isDelete = 0 AND roleId != 2 AND state = #{state}")
    int selUserByStateNum(int state,int offset);  //根据状态查询用户

    @Select("SELECT COUNT(*) FROM user WHERE isDelete = 0 AND roleId != 2 AND name LIKE #{text}")
    int selUserByNameNum(@Param("text")String text,int offset);  //根据用户名查询用户

    @Select("SELECT COUNT(*) FROM user WHERE isDelete = 0 AND roleId != 2 AND name LIKE #{text} AND state = #{state}")
    int selUserByNameAndStateNum(@Param("text")String text,int state,int offset);  //根据用户名查询用户

    @Update("UPDATE user SET state = #{state} WHERE email = #{email}")
    void updUserState(int state,String email);

    @Select("SELECT state FROM user WHERE email = #{email}")
    int selState(String account);  //查询状态
}