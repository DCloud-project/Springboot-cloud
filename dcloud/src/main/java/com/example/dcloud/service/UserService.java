package com.example.dcloud.service;

import com.example.dcloud.mapper.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    public int selectRole(String email){
        return userDao.selRole(email);
    }

    public List<Map> selectUser(int offset){return userDao.selUser(offset);}

    public int addUser(String name,int sex,String email,String password,int roleId){
        if(userDao.checkEmail(email)==null){
            userDao.addUserByAdmin(name,sex,email,password,roleId);
            return 1;
        }else
            return 0;
    }

    public void updateUserByAdmin(String name,int sex,int roleId,String email){
        userDao.updUserByAdmin(name,sex,roleId,email);
    }

    public void deleteUser(String email){
        userDao.delUser(email);
    }

    public int selectUserNum(){return userDao.selUserNum();}

    public List<Map> searchUser(int state,String name,int offset){
        if(state!=-1&&name.equals("%%")==true){
            return userDao.selUserByState(state,offset);
        }else if(name.equals("%%")==false&&state==-1){
            return userDao.selUserByName(name,offset);
        }else{
            return userDao.selUserByNameAndState(name,state,offset);
        }
    }

    public int searchUserNum(int state,String name,int offset){
        if(state!=-1&&name.equals("%%")==true){
            return userDao.selUserByStateNum(state,offset);
        }else if(name.equals("%%")==false&&state==-1){
            return userDao.selUserByNameNum(name,offset);
        }else{
            return userDao.selUserByNameAndStateNum(name,state,offset);
        }
    }

    public void changeUserStateService(String email){
        int state = userDao.selState(email);
        state = state==0?1:0;
        userDao.updUserState(state,email);
    }
}
