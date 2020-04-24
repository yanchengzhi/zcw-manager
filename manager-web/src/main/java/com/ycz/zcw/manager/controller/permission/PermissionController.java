package com.ycz.zcw.manager.controller.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.service.PermissionService;

@Controller
@RequestMapping("/permission/permission/")
public class PermissionController {
    
    @Autowired
    private PermissionService pService;
    
    /**
     * 
     * @Description (加载树形结构数据)
     * @param roleid
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAssignData")
    public Object loadAssignData(Integer roleid) {
       List<Permission> permissions = new ArrayList<>();//存顶级节点的容器
       List<Permission> pers = pService.getAll();//获取所有节点
       //获取当前角色已分配的许可id
       List<Integer> permissionIds = pService.queryPermissionIdsByRoleId(roleid);
       Map<Integer,Permission> map = new HashMap<>();
       for(Permission p:pers) {
          //判断已分配的权限
          if(permissionIds.contains(p.getId())) {
              p.setChecked(true);//分配了的权限勾选
          } else {
              p.setChecked(false);//未分配的不勾选
          }
          //每个节点存map里
          map.put(p.getId(), p);
       }
       
       //父子节组合
       for(Permission p:pers) {
           if(p.getPid()==0) {
               permissions.add(p);//顶级节点
           }else{
               Permission parentPer = map.get(p.getPid());//找出该子节点的顶级父节点
               //组合父子关系
               parentPer.getChildren().add(p);
           }
       }
       return permissions;
    }
    
    @ResponseBody
    @RequestMapping("loadAllData")
    public Object loadAllData() {
        List<Permission> permissions = new ArrayList<>();//存顶级节点的容器
        List<Permission> pers = pService.getAll();//获取所有节点
        Map<Integer,Permission> map = new HashMap<>(); 
        for(Permission p:pers) {
            map.put(p.getId(),p);
        }
        //父子节组合
        for(Permission p:pers) {
            if(p.getPid()==0) {
                permissions.add(p);//所有顶级节点
            }else{
                Permission parentPer = map.get(p.getPid());//找出该子节点的顶级父节点
                //组合父子关系
                parentPer.getChildren().add(p);
            }
        }
        return permissions;
     }
    
    /**
     * 
     * @Description (跳往许可页面)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/permission/index";
    }

}
