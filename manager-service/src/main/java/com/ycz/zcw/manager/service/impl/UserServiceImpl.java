package com.ycz.zcw.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.project.MD5Util;
import com.ycz.project.MyStringUtil;
import com.ycz.zcw.manager.dao.UserMapper;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.UserExample;
import com.ycz.zcw.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper uMapper;

    @Override
    public User queryUser(User user) {
        //查询数据库中是否存在该用户
        UserExample example = new UserExample();
        com.ycz.zcw.manager.pojo.UserExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andLoginacctEqualTo(user.getLoginacct());//查询登录名
        criteria.andUserpswdEqualTo(MD5Util.digest(user.getUserpswd()));//密码查询
        //返回的是一个List集合，要进行处理
        List <User> list = null;
        try {
            list = uMapper.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
        }
          return list.size()==1?list.get(0):null;
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
