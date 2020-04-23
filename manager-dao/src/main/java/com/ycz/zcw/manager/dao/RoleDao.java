package com.ycz.zcw.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.zcw.manager.pojo.Role;

public interface RoleDao {

    List<Role> queryRolesPaged(Map<String, Object> map);

    int getRolesTotal(Map<String, Object> map);

    @Select("select * from t_role where name=#{name}")
    Role queryRoleByName(String name);

    @Select("insert into t_role(name,createtime) values(#{name},#{createtime})")
    void addRole(Role role);

    void deleteRoles(String roleIds);

}
