package com.ycz.zcw.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.constants.Constants;
import com.ycz.zcw.manager.service.PermissionService;
import com.ycz.zcw.manager.service.RoleService;
import com.ycz.zcw.manager.service.UserService;

/**
 * 
 * @ClassName DispatcherController
 * @Description TODO(ҳ���������)
 * @author Administrator
 * @Date 2020��4��20�� ����9:56:53
 * @version 1.0.0
 */
@Controller
public class DispatcherController {
    
    @Autowired
    private UserService uService;
    
    @Autowired
    private PermissionService pService;
    
    @Autowired
    private RoleService rService;
    
    /**
     * 
     * @Description (α��̬��Ч����ʵ���ϻ��Ƕ�̬������ת)
     * @return
     */
    @RequestMapping(value="/main.html")
    public String toMainPage(HttpSession session) {
        //��session���л�ȡ��¼���û�����
        User user = (User) session.getAttribute(Constants.LOGIN_USER);
        if(user==null) {//Ϊ�����û�δ����Ự��Ự����
            return "redirect:login.jsp";//�ض��򵽵�¼ҳ��
        }else {//��Ϊ����ת����ҳ
                //�Ȳ���û�ӵ�е�����Ȩ��
                List<Permission> menus = new ArrayList<>();
                List<Permission> pers = uService.queryPermissionsById(user.getId());
                Map<Integer,Permission> map = new HashMap<>();
                for(Permission p:pers) {
                    map.put(p.getId(),p);
                }
                for(Permission p:pers) {
                    if(p.getPid()==1) {//һ�����ڵ�
                        menus.add(p);
                    }else if(p.getPid()!=0){
                        //���ҳ��ӽڵ�ĸ��ڵ�
                        Permission parent = map.get(p.getPid());
                        //���ӽڵ���װ
                        parent.getChildren().add(p);
                    }
                }
                //���û�ӵ�ж�Ӧ��Ȩ�޲˵��浽session��
                session.setAttribute(Constants.USER_MENUS, menus);
            return "manager/main";
        }
    }

}
