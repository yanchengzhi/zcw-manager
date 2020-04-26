package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.Permission;

public interface PermissionService {
    
    List<Permission> getAllMenus();//��ȡ���в˵������������ʽ

    List<Integer> queryPermissionIdsByRoleId(Integer roleid);//��ѯ��ɫ�ѷ����Ȩ��

    List<Permission> getAll();//��ȡ���нڵ㣬δ�����ʽ

    Permission queryPermissionById(Integer id);//����ID��ѯ�˵�

    Permission queryPermissionByName(String name);//�����Ʋ�ѯ�˵�

    void addPermission(Permission permission);//��Ӳ˵�

    List<Permission> queryTwoMenus();//��ȡ���ж����˵�

    void editPermission(Permission permission);//�޸�Ȩ����Ϣ

    void deleteNode(Integer id);//ɾ���˵�


}
