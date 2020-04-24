package com.ycz.zcw.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface PermissionDao {

    @Select("select permissionid from t_role_permission where roleid=#{roleid}")
    List<Integer> queryPermissionIdsByRoleId(Integer roleid);

    void deleteRolePermissions(Map<String, Object> map);

    void insertRolePermissions(Map<String, Object> map);

}
