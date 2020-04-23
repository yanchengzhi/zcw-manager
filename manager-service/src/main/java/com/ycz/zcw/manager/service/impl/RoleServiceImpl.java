package com.ycz.zcw.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.RoleDao;
import com.ycz.zcw.manager.dao.RoleMapper;
import com.ycz.zcw.manager.pojo.Role;
import com.ycz.zcw.manager.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleMapper rMapper;
    
    @Autowired
    private RoleDao rDao;

    @Override
    public List<Role> queryRolesPaged(Map<String, Object> map) {
        return rDao.queryRolesPaged(map);
    }

    @Override
    public int getRolesTotal(Map<String, Object> map) {
        return rDao.getRolesTotal(map);
    }

    @Override
    public Role queryRoleByName(String name) {
        return rDao.queryRoleByName(name);
    }

    @Override
    public void addRole(Role role) {
        rDao.addRole(role);
    }

    @Override
    public Role queryRoleById(int id) {
        return rMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editRole(Role role) {
        rMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void delete(Integer id) {
        rMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteRoles(String roleIds) {
        rDao.deleteRoles(roleIds);
    }

    @Override
    public List<Role> getAllRoles() {
        return rMapper.selectByExample(null);
    }

}
