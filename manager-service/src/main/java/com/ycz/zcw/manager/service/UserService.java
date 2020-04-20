package com.ycz.zcw.manager.service;

import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(User user);//验证用户

    boolean register(User user);//注册用户

}
