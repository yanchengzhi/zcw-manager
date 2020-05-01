package com.ycz.zcw.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.User;

public interface UserDao {

    int getUsersTotal(Map<String, Object> map);

    List<User> queryUsersPaged(Map<String, Object> map);

    @Select("select * from t_user where loginacct=#{loginacct}")
    User queryUserByName(String loginacct);

    void deleteUsers(String userIds);

    @Select("select roleid from t_user_role where userid=#{userid}")
    List<Integer> queryRoleIdsByUserId(Integer id);

    void insertUserRoles(Map<String, Object> map);

    void deleteUserRoles(Map<String, Object> map);

    List<Permission> queryPermissionsById(Integer id);

    @Select("select * from t_user where email=#{email}")
    User checkEmail(String email);

}
