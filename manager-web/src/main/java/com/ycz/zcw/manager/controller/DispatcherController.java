package com.ycz.zcw.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycz.zcw.manager.pojo.constants.Constants;

/**
 * 
 * @ClassName DispatcherController
 * @Description TODO(这个控制器用来重定向到主页面)
 * @author Administrator
 * @Date 2020年4月20日 下午9:56:53
 * @version 1.0.0
 */
@Controller
public class DispatcherController {
    
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
            return "manager/main";
        }
    }

}
