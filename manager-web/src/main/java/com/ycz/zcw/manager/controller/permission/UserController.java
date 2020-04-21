package com.ycz.zcw.manager.controller.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Page;
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
    
    /**
     * 
     * @Description (用户登录)
     * @param user
     * @param session
     * @return
     */
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
    
    /**
     * 
     * @Description (跳往用户页面)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/user/index";
    }
    
    /**
     * 
     * @Description (分页查询)
     * @param page
     * @param pageSize
     * @param queryText
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Object list(
            @RequestParam(value="page",required = true) Integer page,
            @RequestParam(value="pageSize",required = false) Integer pageSize,
            @RequestParam(value="queryText",required = false) String queryText
            ) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("offset",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            map.put("queryText",queryText);
            //查询需要的记录
            List<User> users = uService.queryUsersPaged(map);
            //获取总记录条数
            int totalSize = uService.getUsersTotal(map);
            //获取最大页码数
            int maxPage = (totalSize%pageSize==0)?totalSize/pageSize:(totalSize/pageSize)+1;
            //使用分页对象封装数据
            Page<User> userPage = new Page<>();
            userPage.setDatas(users);
            userPage.setMaxPage(maxPage);
            userPage.setPage(page);
            userPage.setTotalSize(totalSize);
            result.setData(userPage);//封装到result中返给前台
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
