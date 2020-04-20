package com.ycz.zcw.manager.controller.permission;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.constants.Constants;
import com.ycz.zcw.manager.service.UserService;

/**
 * 
 * @ClassName UserController
 * @Description TODO(�û�������)
 * @author Administrator
 * @Date 2020��4��19�� ����5:51:42
 * @version 1.0.0
 */
@Controller
@RequestMapping("/permission/user/")
public class UserController {
    
    @Autowired
    private UserService uService;
    
    /**
     * 
     * @Description (�û�ע��)
     * @param user
     * @return
     */
    @RequestMapping("regist")
    public String regist(User user,Model model,HttpSession session){
        boolean flag = uService.register(user);
        if(flag) {//ע��ɹ���������ҳ
            session.setAttribute(Constants.LOGIN_USER, user);//���û�����浽session����
            return "redirect:/main.html";
        }else {//ע��ʧ�ܣ��ض���ע��ҳ��
            model.addAttribute("regError","�û����ѱ�ռ�ã�");
            model.addAttribute("user",user);
            return "forward:/register.jsp";
        }
    }
    
    @RequestMapping("login")
    public String login(User user,HttpSession session) {
        User dbUser = uService.queryUser(user);
        if(dbUser==null) {//��¼ʧ��ʱ
            session.setAttribute("errorUser", user);
            session.setAttribute("msg", "��½ʧ�ܣ�");
            //�ض��򵽵�¼ҳ��
            return "redirect:/login.jsp";
        }else {//��¼�ɹ�ʱ
            session.setAttribute(Constants.LOGIN_USER, dbUser);
            return "redirect:/main.html";
        }
    }

}
