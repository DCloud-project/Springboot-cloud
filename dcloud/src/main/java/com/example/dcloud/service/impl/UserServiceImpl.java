package com.example.dcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dcloud.entity.User;
import com.example.dcloud.mapper.UserMapper;
import com.example.dcloud.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public int registerService(String email,String password){
        if(userMapper.checkEmail(email)==null){
            userMapper.addUser(email,password);
            return 1;
        }
        else{
            return 0;
        }
    }
    @Override
    public int loginByPwd(String email,String password){
        if(userMapper.checkEmail(email)==null){
            return 0;
        }
        else if(userMapper.checkPassword(email).equals(password)==false){
            return 1;
        }
        else{
            return 2;
        }
    }
    @Override
    public int loginByCode(String email){
        if(userMapper.checkEmail(email)==null){
            return 0;
        }
        else{
            return 1;
        }
    }
    @Override
    public int selectRole(String email){
        return userMapper.selRole(email);
    }
    @Override
    public List<Map> selectUser(int offset){return userMapper.selUser(offset);}
    @Override
    public int addUser(String name,int sex,String email,String password,int roleId){
        if(userMapper.checkEmail(email)==null){
            userMapper.addUserByAdmin(name,sex,email,password,roleId);
            return 1;
        }else
            return 0;
    }
    @Override
    public void updateUserByAdmin(String name,int sex,int roleId,String email){
        userMapper.updUserByAdmin(name,sex,roleId,email);
    }
    @Override
    public void deleteUser(String email){
        userMapper.delUser(email);
    }
    @Override
    public int selectUserNum(){return userMapper.selUserNum();}
    @Override
    public List<Map> searchUser(int state,String name,int offset){
        if(state!=-1&&name.equals("%%")==true){
            return userMapper.selUserByState(state,offset);
        }else if(name.equals("%%")==false&&state==-1){
            return userMapper.selUserByName(name,offset);
        }else{
            return userMapper.selUserByNameAndState(name,state,offset);
        }
    }
    @Override
    public int searchUserNum(int state,String name,int offset){
        if(state!=-1&&name.equals("%%")==true){
            return userMapper.selUserByStateNum(state,offset);
        }else if(name.equals("%%")==false&&state==-1){
            return userMapper.selUserByNameNum(name,offset);
        }else{
            return userMapper.selUserByNameAndStateNum(name,state,offset);
        }
    }
    @Override
    public void changeUserStateService(String email){
        int state = userMapper.selState(email);
        state = state==0?1:0;
        userMapper.updUserState(state,email);
    }
}
