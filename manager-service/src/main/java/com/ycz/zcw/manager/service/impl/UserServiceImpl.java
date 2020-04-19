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
        // MD5�����û�����
        String crypt = MD5Util.digest(user.getUserpswd());
        // ������������
        user.setUserpswd(crypt);
        user.setUsername(user.getLoginacct());
        user.setCreatetime(MyStringUtil.date2String(new Date()));
        int res = 0;
        try {
            res = uMapper.insertSelective(user);
        } catch (Exception e) {// �����쳣˵���û��ظ������ݿ�ᱨ�쳣
            return false;
        }
        // �ж������Ƿ񱣴�ɹ�
        return res == 1 ? true : false;
    }

}
