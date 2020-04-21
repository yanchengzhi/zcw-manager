package com.ycz.zcw.manager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.constants.Constants;
import com.ycz.zcw.manager.service.PermissionService;

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
    private PermissionService pService;
    
    /**
     * 
     * @Description (α��̬��Ч����ʵ���ϻ��Ƕ�̬������ת)
     * @return
     */
    @RequestMapping(value="/main.html")
    public String toMainPage(HttpSession session) {
        //��session���л�ȡ��¼���û�����
        Object obj = session.getAttribute(Constants.LOGIN_USER);
        if(obj==null) {//Ϊ�����û�δ����Ự��Ự����
            return "redirect:login.jsp";//�ض��򵽵�¼ҳ��
        }else {//��Ϊ����ת����ҳ
            //session��û�д�˵�ʱ�����ܱ��û������
            if(session.getAttribute(Constants.USER_MENUS)==null) {
                //�Ȳ�����в˵�����ʾ��������
                List<Permission> menus = pService.getAllMenus();
                //���鵽�Ĳ˵��浽session��
                session.setAttribute(Constants.USER_MENUS, menus);
            }
            return "manager/main";
        }
    }

}
