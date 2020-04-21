package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(User user);//��֤�û�

    boolean register(User user);//ע���û�

    List<User> getAll();//��ѯ�����û�

    int getUsersTotal(Map<String, Object> map);//��ȡ�ܵļ�¼����

    List<User> queryUsersPaged(Map<String, Object> map);//��ҳ��ѯ�û�

}
