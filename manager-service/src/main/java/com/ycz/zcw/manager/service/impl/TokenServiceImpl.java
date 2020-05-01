package com.ycz.zcw.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.UserTokenMapper;
import com.ycz.zcw.manager.pojo.UserToken;
import com.ycz.zcw.manager.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
    
    @Autowired
    private UserTokenMapper utMapper;

    @Override
    public void saveToken(UserToken uToken) {
        utMapper.insertSelective(uToken);
    }

    @Override
    public UserToken queryByToken(String token) {
        return utMapper.queryByToken(token);
    }

}
