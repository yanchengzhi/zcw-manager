package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.Permission;

public interface PermissionService {
    
    List<Permission> getAllMenus();//��ȡ���в˵������������ʽ

    List<Integer> queryPermissionIdsByRoleId(Integer roleid);//��ѯ��ɫ�ѷ����Ȩ��

    List<Permission> getAll();//��ȡ���нڵ㣬δ�����ʽ

}
