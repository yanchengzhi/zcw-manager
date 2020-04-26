package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.Permission;

public interface PermissionService {
    
    List<Permission> getAllMenus();//获取所有菜单，父子组合形式

    List<Integer> queryPermissionIdsByRoleId(Integer roleid);//查询角色已分配的权限

    List<Permission> getAll();//获取所有节点，未组合形式

    Permission queryPermissionById(Integer id);//按照ID查询菜单

    Permission queryPermissionByName(String name);//按名称查询菜单

    void addPermission(Permission permission);//添加菜单

    List<Permission> queryTwoMenus();//获取所有二级菜单

    void editPermission(Permission permission);//修改权限信息

    void deleteNode(Integer id);//删除菜单


}
