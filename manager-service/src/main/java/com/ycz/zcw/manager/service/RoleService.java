package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Role;

public interface RoleService {

    List<Role> queryRolesPaged(Map<String, Object> map);//分页查询

    int getRolesTotal(Map<String, Object> map);//获取角色表中总记录条数

    Role queryRoleByName(String name);//查询角色

    void addRole(Role role);//添加角色

    Role queryRoleById(int id);//按照角色ID查找角色

    void editRole(Role role);//修改角色

    void delete(Integer id);//单条记录删除

    void deleteRoles(String roleIds);//批量删除角色

    List<Role> getAllRoles();//获取所有角色

    void insertRolePermission(Map<String, Object> map);//为角色添加权限

}
