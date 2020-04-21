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
 * @Description TODO(页面调度中心)
 * @author Administrator
 * @Date 2020年4月20日 下午9:56:53
 * @version 1.0.0
 */
@Controller
public class DispatcherController {
    
    @Autowired
    private PermissionService pService;
    
    /**
     * 
     * @Description (伪静态化效果，实际上还是动态处理跳转)
     * @return
     */
    @RequestMapping(value="/main.html")
    public String toMainPage(HttpSession session) {
        //从session域中获取登录的用户对象
        Object obj = session.getAttribute(Constants.LOGIN_USER);
        if(obj==null) {//为空则用户未发起会话或会话过期
            return "redirect:login.jsp";//重定向到登录页面
        }else {//不为空跳转到主页
            //session中没有存菜单时（可能被用户清除）
            if(session.getAttribute(Constants.USER_MENUS)==null) {
                //先查出所有菜单，显示到左侧边栏
                List<Permission> menus = pService.getAllMenus();
                //将查到的菜单存到session中
                session.setAttribute(Constants.USER_MENUS, menus);
            }
            return "manager/main";
        }
    }

}
