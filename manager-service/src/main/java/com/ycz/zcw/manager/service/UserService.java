package com.ycz.zcw.manager.service;

import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(User user);//��֤�û�

    boolean register(User user);//ע���û�

}
