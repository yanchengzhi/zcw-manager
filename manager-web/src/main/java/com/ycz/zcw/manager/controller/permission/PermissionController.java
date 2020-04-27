package com.ycz.zcw.manager.controller.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.service.PermissionService;

/**
 * 
 * @ClassName PermissionController
 * @Description TODO(权限控制器)
 * @author Administrator
 * @Date 2020年4月27日 下午3:49:18
 * @version 1.0.0
 */
@Controller
@RequestMapping("/permission/permission/")
public class PermissionController {

    @Autowired
    private PermissionService pService;

    /**
     * @Description (加载用户权限对应的菜单)
     * @param roleid
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAssignData")
    public Object loadAssignData(Integer roleid) {
        List<Permission> permissions = new ArrayList<>();// 存顶级节点的容器
        List<Permission> pers = pService.getAll();// 获取所有节点
        // 获取当前角色已分配的许可id
        List<Integer> permissionIds = pService.queryPermissionIdsByRoleId(roleid);
        Map<Integer, Permission> map = new HashMap<>();
        for (Permission p : pers) {
            // 判断已分配的权限
            if (permissionIds.contains(p.getId())) {
                p.setChecked(true);// 分配了的权限勾选
            } else {
                p.setChecked(false);// 未分配的不勾选
            }
            // 每个节点存map里
            map.put(p.getId(), p);
        }

        // 父子节组合
        for (Permission p : pers) {
            if (p.getPid() == 0) {
                permissions.add(p);// 顶级节点
            } else {
                Permission parentPer = map.get(p.getPid());// 找出该子节点的顶级父节点
                // 组合父子关系
                parentPer.getChildren().add(p);
            }
        }
        return permissions;
    }

    /**
     * @Description (加载所有菜单)
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAllData")
    public Object loadAllData() {
        List<Permission> permissions = new ArrayList<>();// 存顶级节点的容器
        List<Permission> pers = pService.getAll();// 获取所有节点
        Map<Integer, Permission> map = new HashMap<>();
        for (Permission p : pers) {
            map.put(p.getId(), p);
        }
        // 父子节组合
        for (Permission p : pers) {
            if (p.getPid() == 0) {
                permissions.add(p);// 所有顶级节点
            } else {
                Permission parentPer = map.get(p.getPid());// 找出该子节点的顶级父节点
                // 组合父子关系
                parentPer.getChildren().add(p);
            }
        }
        return permissions;
    }

    /**
     * @Description (跳往许可页面)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/permission/index";
    }

    /**
     * @Description (获取子菜单的父菜单)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getParentMenu")
    public Object getParentMenu(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            // 查询出要添加菜单的父菜单
            Permission parent = pService.queryPermissionById(id);
            result.setData(parent);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (校验菜单名称是否重复)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateMenu")
    public Object validateMenu(String name) {
        AjaxResult result = new AjaxResult();
        // 查询名称是否重复
        Permission permission = pService.queryPermissionByName(name);
        if (permission == null) {// 不重复
            result.setSuccess(true);
        } else {// 重复
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("addPermission")
    public Object addPermission(Permission permission) {
        AjaxResult result = new AjaxResult();
        try {
            // 判断是否存在
            Permission p = pService.queryPermissionByName(permission.getName());
            if (p == null) {// 不存在，可添加
                // 执行添加操作
                permission.setIcon("glyphicon glyphicon-plus");
                pService.addPermission(permission);
                result.setSuccess(true);
            } else {// 重复，不可添加
                result.setData("该菜单名已存在！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (查询菜单)
     * @return
     */
    @ResponseBody
    @RequestMapping("getMenu")
    public Object getMenu(Integer id) {
        AjaxResult result = new AjaxResult();
        Permission permission = pService.queryPermissionById(id);
        result.setData(permission);
        result.setSuccess(true);
        return result;
    }

    /**
     * @Description (获取所有二级菜单)
     * @return
     */
    @ResponseBody
    @RequestMapping("getTwoMenus")
    public Object getTwoMenus() {
        AjaxResult result = new AjaxResult();
        List<Permission> pers = pService.queryTwoMenus();
        result.setData(pers);
        result.setSuccess(true);
        return result;
    }

    /**
     * @Description (修改权限信息)
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("editPermission")
    public Object editPermission(Permission permission) {
        AjaxResult result = new AjaxResult();
        try {
            permission.setIcon("glyphicon glyphicon-plus");//图标暂时默认，后期添加文本框选择功能
            pService.editPermission(permission);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("该菜单名已被占用！");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("deleteNode")
    public Object deleteNode(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            pService.deleteNode(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
