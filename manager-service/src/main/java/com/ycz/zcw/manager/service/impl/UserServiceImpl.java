package com.ycz.zcw.manager.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.project.MD5Util;
import com.ycz.project.MyStringUtil;
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

    @Override
    public boolean register(User user) {
        // MD5加密用户密码
        String crypt = MD5Util.digest(user.getUserpswd());
        // 设置密码密文
        user.setUserpswd(crypt);
        user.setUsername(user.getLoginacct());
        user.setCreatetime(MyStringUtil.date2String(new Date()));
        int res = 0;
        try {
            res = uMapper.insertSelective(user);
        } catch (Exception e) {// 发生异常说明用户重复，数据库会报异常
            return false;
        }
        // 判断数据是否保存成功
        return res == 1 ? true : false;
    }

}
