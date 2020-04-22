package com.ycz.zcw.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.zcw.manager.pojo.User;

public interface UserDao {

    int getUsersTotal(Map<String, Object> map);

    List<User> queryUsersPaged(Map<String, Object> map);

    @Select("select * from t_user where loginacct=#{loginacct}")
    User queryUserByName(String loginacct);

    void deleteUsers(String userIds);

}
