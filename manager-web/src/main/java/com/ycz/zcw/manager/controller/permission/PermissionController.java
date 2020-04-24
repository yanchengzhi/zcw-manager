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
     * @Description (�������νṹ����)
     * @param roleid
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAssignData")
    public Object loadAssignData(Integer roleid) {
       List<Permission> permissions = new ArrayList<>();//�涥���ڵ������
       List<Permission> pers = pService.getAll();//��ȡ���нڵ�
       //��ȡ��ǰ��ɫ�ѷ�������id
       List<Integer> permissionIds = pService.queryPermissionIdsByRoleId(roleid);
       Map<Integer,Permission> map = new HashMap<>();
       for(Permission p:pers) {
          //�ж��ѷ����Ȩ��
          if(permissionIds.contains(p.getId())) {
              p.setChecked(true);//�����˵�Ȩ�޹�ѡ
          } else {
              p.setChecked(false);//δ����Ĳ���ѡ
          }
          //ÿ���ڵ��map��
          map.put(p.getId(), p);
       }
       
       //���ӽ����
       for(Permission p:pers) {
           if(p.getPid()==0) {
               permissions.add(p);//�����ڵ�
           }else{
               Permission parentPer = map.get(p.getPid());//�ҳ����ӽڵ�Ķ������ڵ�
               //��ϸ��ӹ�ϵ
               parentPer.getChildren().add(p);
           }
       }
       return permissions;
    }
    
    @ResponseBody
    @RequestMapping("loadAllData")
    public Object loadAllData() {
        List<Permission> permissions = new ArrayList<>();//�涥���ڵ������
        List<Permission> pers = pService.getAll();//��ȡ���нڵ�
        Map<Integer,Permission> map = new HashMap<>(); 
        for(Permission p:pers) {
            map.put(p.getId(),p);
        }
        //���ӽ����
        for(Permission p:pers) {
            if(p.getPid()==0) {
                permissions.add(p);//���ж����ڵ�
            }else{
                Permission parentPer = map.get(p.getPid());//�ҳ����ӽڵ�Ķ������ڵ�
                //��ϸ��ӹ�ϵ
                parentPer.getChildren().add(p);
            }
        }
        return permissions;
     }
    
    /**
     * 
     * @Description (�������ҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/permission/index";
    }

}
