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
    private PermissionService pService;
    
    @Autowired
    private RoleService rService;
    
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

}
