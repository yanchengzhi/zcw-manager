package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.User;

public interface UserService {
    
    User queryUser(User user);//验证用户

    boolean register(User user);//注册用户

    List<User> getAll();//查询所有用户

    int getUsersTotal(Map<String, Object> map);//获取总的记录条数

    List<User> queryUsersPaged(Map<String, Object> map);//分页查询用户

    void addUser(User user);//添加用户

    User queryUserById(int id);//按照id查找用户

    void editUser(User user);//修改用户

    void delete(Integer id);//按id单个删除

    User queryUserByName(String loginacct);//按登录名查询

    void deleteUsers(String userIds);//批量删除用户

    List<Integer> queryRoleIdsByUserId(Integer id);//查询用户已分配角色的ID

    void insertUserRoles(Map<String, Object> map);//为用户分配角色

    void deleteUserRoles(Map<String, Object> map);//取消用户的角色

    List<Permission> queryPermissionsById(Integer id);//查询用户拥有的权限

    boolean sendEmail(String email);//发送邮件给指定用户
    
    User checkEmail(String email);//检查邮箱是否存在

    int updateUserPass(String password,User user);//重置用户密码

}
