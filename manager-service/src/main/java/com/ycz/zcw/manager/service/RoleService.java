package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Role;

public interface RoleService {

    List<Role> queryRolesPaged(Map<String, Object> map);//��ҳ��ѯ

    int getRolesTotal(Map<String, Object> map);//��ȡ��ɫ�����ܼ�¼����

    Role queryRoleByName(String name);//��ѯ��ɫ

    void addRole(Role role);//��ӽ�ɫ

    Role queryRoleById(int id);//���ս�ɫID���ҽ�ɫ

    void editRole(Role role);//�޸Ľ�ɫ

    void delete(Integer id);//������¼ɾ��

    void deleteRoles(String roleIds);//����ɾ����ɫ

    List<Role> getAllRoles();//��ȡ���н�ɫ

    void insertRolePermission(Map<String, Object> map);//Ϊ��ɫ���Ȩ��

}
