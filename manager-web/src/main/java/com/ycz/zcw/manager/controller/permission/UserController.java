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
 * @Description TODO(用户控制器)
 * @author Administrator
 * @Date 2020年4月19日 下午5:51:42
 * @version 1.0.0
 */
@Controller
@RequestMapping("/permission/user/")
public class UserController {
    
    @Autowired
    private UserService uService;
    
    /**
     * 
     * @Description (用户注册)
     * @param user
     * @return
     */
    @RequestMapping("regist")
    public String regist(User user,Model model,HttpSession session){
        boolean flag = uService.register(user);
        if(flag) {//注册成功，跳往主页
            session.setAttribute(Constants.LOGIN_USER, user);//将用户对象存到session域中
            return "redirect:/main.html";
        }else {//注册失败，重定向到注册页面
            model.addAttribute("regError","用户名已被占用！");
            model.addAttribute("user",user);
            return "forward:/register.jsp";
        }
    }
    
    @RequestMapping("login")
    public String login(User user,HttpSession session) {
        User dbUser = uService.queryUser(user);
        if(dbUser==null) {//登录失败时
            session.setAttribute("errorUser", user);
            session.setAttribute("msg", "登陆失败！");
            //重定向到登录页面
            return "redirect:/login.jsp";
        }else {//登录成功时
            session.setAttribute(Constants.LOGIN_USER, dbUser);
            return "redirect:/main.html";
        }
    }

}
