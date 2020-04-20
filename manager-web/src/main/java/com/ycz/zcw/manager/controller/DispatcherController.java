package com.ycz.zcw.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycz.zcw.manager.pojo.constants.Constants;

/**
 * 
 * @ClassName DispatcherController
 * @Description TODO(��������������ض�����ҳ��)
 * @author Administrator
 * @Date 2020��4��20�� ����9:56:53
 * @version 1.0.0
 */
@Controller
public class DispatcherController {
    
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
            return "manager/main";
        }
    }

}
