package com.ycz.zcw.manager.dao;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.User;

public interface UserDao {

    int getUsersTotal(Map<String, Object> map);

    List<User> queryUsersPaged(Map<String, Object> map);

}
