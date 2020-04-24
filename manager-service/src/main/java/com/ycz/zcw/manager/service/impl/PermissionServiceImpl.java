package com.ycz.zcw.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.PermissionDao;
import com.ycz.zcw.manager.dao.PermissionMapper;
import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
    
    @Autowired
    private PermissionMapper pMapper;
    
    @Autowired
    private PermissionDao pDao;

    @Override
    public List<Permission> getAllMenus() {
        //查询所有菜单
        List<Permission> menus = pMapper.selectByExample(null);
        List<Permission> pMenus = new ArrayList<>();//顶级父菜单
        Map<Integer, Permission> map = new HashMap<>();
        //将所有菜单的ID作为键，菜单作为值存到map集合里
        for(Permission p:menus) {
            map.put(p.getId(), p);
        }
        for(Permission p:menus) {
            if(p.getPid()==1) {//找出所有的父菜单，添加到List中
                pMenus.add(p);
            }else if(p.getPid()!=0){//找出所有子菜单，加到对应的父菜单里
                Integer pId = p.getPid();//获取子菜单的pId
                Permission permission = map.get(pId);//根据pId获取父菜单
                //获取父菜单的所有子菜单
                List<Permission> cMenus = permission.getChildren();
                if(cMenus!=null) {//当前有子菜单时
                    cMenus.add(p);//直接添加进来
                }else {//当前没有子菜单时,cMenus是空的
                    cMenus = new ArrayList<>();//需要new一个List
                    cMenus.add(p);
                    //组合父子菜单
                    permission.setChildren(cMenus);
                }
            }
        }
        return pMenus;
    }

    @Override
    public List<Integer> queryPermissionIdsByRoleId(Integer roleid) {
        return pDao.queryPermissionIdsByRoleId(roleid);
    }

    @Override
    public List<Permission> getAll() {
        return pMapper.selectByExample(null);
    }

}
