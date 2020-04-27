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
 * @Description TODO(Ȩ�޿�����)
 * @author Administrator
 * @Date 2020��4��27�� ����3:49:18
 * @version 1.0.0
 */
@Controller
@RequestMapping("/permission/permission/")
public class PermissionController {

    @Autowired
    private PermissionService pService;

    /**
     * @Description (�����û�Ȩ�޶�Ӧ�Ĳ˵�)
     * @param roleid
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAssignData")
    public Object loadAssignData(Integer roleid) {
        List<Permission> permissions = new ArrayList<>();// �涥���ڵ������
        List<Permission> pers = pService.getAll();// ��ȡ���нڵ�
        // ��ȡ��ǰ��ɫ�ѷ�������id
        List<Integer> permissionIds = pService.queryPermissionIdsByRoleId(roleid);
        Map<Integer, Permission> map = new HashMap<>();
        for (Permission p : pers) {
            // �ж��ѷ����Ȩ��
            if (permissionIds.contains(p.getId())) {
                p.setChecked(true);// �����˵�Ȩ�޹�ѡ
            } else {
                p.setChecked(false);// δ����Ĳ���ѡ
            }
            // ÿ���ڵ��map��
            map.put(p.getId(), p);
        }

        // ���ӽ����
        for (Permission p : pers) {
            if (p.getPid() == 0) {
                permissions.add(p);// �����ڵ�
            } else {
                Permission parentPer = map.get(p.getPid());// �ҳ����ӽڵ�Ķ������ڵ�
                // ��ϸ��ӹ�ϵ
                parentPer.getChildren().add(p);
            }
        }
        return permissions;
    }

    /**
     * @Description (�������в˵�)
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAllData")
    public Object loadAllData() {
        List<Permission> permissions = new ArrayList<>();// �涥���ڵ������
        List<Permission> pers = pService.getAll();// ��ȡ���нڵ�
        Map<Integer, Permission> map = new HashMap<>();
        for (Permission p : pers) {
            map.put(p.getId(), p);
        }
        // ���ӽ����
        for (Permission p : pers) {
            if (p.getPid() == 0) {
                permissions.add(p);// ���ж����ڵ�
            } else {
                Permission parentPer = map.get(p.getPid());// �ҳ����ӽڵ�Ķ������ڵ�
                // ��ϸ��ӹ�ϵ
                parentPer.getChildren().add(p);
            }
        }
        return permissions;
    }

    /**
     * @Description (�������ҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/permission/index";
    }

    /**
     * @Description (��ȡ�Ӳ˵��ĸ��˵�)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getParentMenu")
    public Object getParentMenu(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            // ��ѯ��Ҫ��Ӳ˵��ĸ��˵�
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
     * @Description (У��˵������Ƿ��ظ�)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateMenu")
    public Object validateMenu(String name) {
        AjaxResult result = new AjaxResult();
        // ��ѯ�����Ƿ��ظ�
        Permission permission = pService.queryPermissionByName(name);
        if (permission == null) {// ���ظ�
            result.setSuccess(true);
        } else {// �ظ�
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("addPermission")
    public Object addPermission(Permission permission) {
        AjaxResult result = new AjaxResult();
        try {
            // �ж��Ƿ����
            Permission p = pService.queryPermissionByName(permission.getName());
            if (p == null) {// �����ڣ������
                // ִ����Ӳ���
                permission.setIcon("glyphicon glyphicon-plus");
                pService.addPermission(permission);
                result.setSuccess(true);
            } else {// �ظ����������
                result.setData("�ò˵����Ѵ��ڣ�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (��ѯ�˵�)
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
     * @Description (��ȡ���ж����˵�)
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
     * @Description (�޸�Ȩ����Ϣ)
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("editPermission")
    public Object editPermission(Permission permission) {
        AjaxResult result = new AjaxResult();
        try {
            permission.setIcon("glyphicon glyphicon-plus");//ͼ����ʱĬ�ϣ���������ı���ѡ����
            pService.editPermission(permission);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�ò˵����ѱ�ռ�ã�");
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
