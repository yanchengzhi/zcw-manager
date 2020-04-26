package com.ycz.zcw.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.zcw.manager.pojo.Permission;

public interface PermissionDao {

    @Select("select permissionid from t_role_permission where roleid=#{roleid}")
    List<Integer> queryPermissionIdsByRoleId(Integer roleid);

    void deleteRolePermissions(Map<String, Object> map);

    void insertRolePermissions(Map<String, Object> map);

    @Select("select * from t_permission where id=#{id}")
    Permission queryPermissionById(Integer id);

    @Select("select * from t_permission where name=#{name}")
    Permission queryPermissionByName(String name);

    @Select("select * from t_permission where pid=1")
    List<Permission> queryTwoMenus();


}
