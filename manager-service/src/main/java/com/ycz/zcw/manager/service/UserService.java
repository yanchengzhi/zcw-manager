package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(User user);//��֤�û�

    boolean register(User user);//ע���û�

    List<User> getAll();//��ѯ�����û�

    int getUsersTotal(Map<String, Object> map);//��ȡ�ܵļ�¼����

    List<User> queryUsersPaged(Map<String, Object> map);//��ҳ��ѯ�û�

    void addUser(User user);//����û�

    User queryUserById(int id);//����id�����û�

    void editUser(User user);//�޸��û�

    void delete(Integer id);//��id����ɾ��

    User queryUserByName(String loginacct);//����¼����ѯ

    void deleteUsers(String userIds);//����ɾ���û�

    List<Integer> queryRoleIdsByUserId(Integer id);//��ѯ�û��ѷ����ɫ��ID

    void insertUserRoles(Map<String, Object> map);//Ϊ�û������ɫ

    void deleteUserRoles(Map<String, Object> map);//ȡ���û��Ľ�ɫ

    List<Permission> queryPermissionsById(Integer id);//��ѯ�û�ӵ�е�Ȩ��

    boolean sendEmail(String email);//�����ʼ���ָ���û�
    
    User checkEmail(String email);//��������Ƿ����

    int updateUserPass(String password,User user);//�����û�����

}
