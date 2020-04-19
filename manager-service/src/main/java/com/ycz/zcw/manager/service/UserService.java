package com.ycz.zcw.manager.service;

import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(Integer id);

    boolean register(User user);

}
