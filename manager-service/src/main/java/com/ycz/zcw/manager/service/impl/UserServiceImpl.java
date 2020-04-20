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
        //��ѯ���ݿ����Ƿ���ڸ��û�
        UserExample example = new UserExample();
        com.ycz.zcw.manager.pojo.UserExample.Criteria criteria = example.createCriteria();
        //���ò�ѯ����
        criteria.andLoginacctEqualTo(user.getLoginacct());//��ѯ��¼��
        criteria.andUserpswdEqualTo(MD5Util.digest(user.getUserpswd()));//�����ѯ
        //���ص���һ��List���ϣ�Ҫ���д���
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
