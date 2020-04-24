package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.Permission;

public interface PermissionService {
    
    List<Permission> getAllMenus();//获取所有菜单，父子组合形式

    List<Integer> queryPermissionIdsByRoleId(Integer roleid);//查询角色已分配的权限

    List<Permission> getAll();//获取所有节点，未组合形式

}
