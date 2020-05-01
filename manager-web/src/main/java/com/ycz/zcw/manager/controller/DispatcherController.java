package com.ycz.zcw.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.UserToken;
import com.ycz.zcw.manager.pojo.constants.Constants;
import com.ycz.zcw.manager.service.TokenService;
import com.ycz.zcw.manager.service.UserService;

/**
 * 
 * @ClassName DispatcherController
 * @Description TODO(页面调度中心)
 * @author Administrator
 * @Date 2020年4月20日 下午9:56:53
 * @version 1.0.0
 */
@Controller
public class DispatcherController {
    
    @Autowired
    private UserService uService;
    
    @Autowired
    private TokenService tService;
    
    
    /**
     * 
     * @Description (伪静态化效果，实际上还是动态处理跳转)
     * @return
     */
    @RequestMapping(value="/main.html")
    public String toMainPage(HttpSession session) {
        //从session域中获取登录的用户对象
        User user = (User) session.getAttribute(Constants.LOGIN_USER);
        if(user==null) {//为空则用户未发起会话或会话过期
            return "redirect:login.jsp";//重定向到登录页面
        }else {//不为空跳转到主页
                //先查出用户拥有的所有权限
                List<Permission> menus = new ArrayList<>();
                List<Permission> pers = uService.queryPermissionsById(user.getId());
                Map<Integer,Permission> map = new HashMap<>();
                for(Permission p:pers) {
                    map.put(p.getId(),p);
                }
                for(Permission p:pers) {
                    if(p.getPid()==1) {//一级父节点
                        menus.add(p);
                    }else if(p.getPid()!=0){
                        //先找出子节点的父节点
                        Permission parent = map.get(p.getPid());
                        //父子节点组装
                        parent.getChildren().add(p);
                    }
                }
                //将用户拥有对应的权限菜单存到session中
                session.setAttribute(Constants.USER_MENUS, menus);
            return "manager/main";
        }
    }
    
    /**
     * 
     * @Description (将重置密码的链接以邮件的形式发送给用户)
     * @param email
     * @param model
     * @return
     */
    @RequestMapping("sendEmail")
    public String sendMail(String email,Model model) {
        //按照邮箱查找用户
        boolean flg = uService.sendEmail(email);
        if(flg) {
            model.addAttribute("msg","已向您的邮箱"+email+"发送了一封邮件，请注意查收！");
        }else {
            model.addAttribute("msg","已向该邮箱发送了一封邮件！");
        }
        return "success";
    }
    
    /**
     * 
     * @Description (跳转到重置密码页面)
     * @return
     */
    @RequestMapping("resetPass")
    public String resetPass() {
        return "reset_password";
    }
    
    /**
     * 
     * @Description (修改密码)
     * @param password
     * @param token
     * @return
     */
    @RequestMapping("updatePass")
    public String updatePass(String password,String token,Model model) {
        UserToken uToken = tService.queryByToken(token);//查出对应的令牌
        //按照令牌查找用户
        User user = uService.queryUserById(uToken.getUserId());
        //重置密码
        int res = uService.updateUserPass(password,user);
        if(res==1) {
            model.addAttribute("msg","密码重置成功！请重新登录！");
        }else {
            model.addAttribute("msg","重置密码链接失效，请重新发送！");
        }
        return "success";
    }

}
