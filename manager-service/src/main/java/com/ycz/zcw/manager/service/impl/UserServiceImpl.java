package com.ycz.zcw.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.UserMapper;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper uMapper;

    @Override
    public User queryUser(Integer id) {
        return uMapper.selectByPrimaryKey(id);
    }

}
