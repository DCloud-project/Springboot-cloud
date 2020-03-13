package com.example.dcloud.service;

import com.example.dcloud.mapper.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    UserDao userDao;
    public int registerService(String email,String password){
        if(userDao.checkEmail(email)==null){
            userDao.addUser(email,password);
            return 1;
        }
        else{
            return 0;
        }
    }

    public int loginByPwd(String email,String password){
        if(userDao.checkEmail(email)==null){
            return 0;
        }
        else if(userDao.checkPassword(email).equals(password)==false){
            return 1;
        }
        else{
            return 2;
        }
    }

    public int loginByCode(String email){
        if(userDao.checkEmail(email)==null){
            return 0;
        }
        else{
            return 1;
        }
    }
}
