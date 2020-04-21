package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(User user);//验证用户

    boolean register(User user);//注册用户

    List<User> getAll();//查询所有用户

    int getUsersTotal(Map<String, Object> map);//获取总的记录条数

    List<User> queryUsersPaged(Map<String, Object> map);//分页查询用户

}
