package com.ycz.zcw.manager.controller.permission;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.ycz.project.MD5Util;
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
    
    /**
     * 
     * @Description (跳往用户添加页面)
     * @return
     */
    @RequestMapping("goToAdd")
    public String goToAdd() {
        return "manager/permission/user/add";
    }
    
    /**
     * 
     * @Description (验证账号是否重复)
     * @param loginacct
     * @return
     */
    @ResponseBody
    @RequestMapping("validateLoginacct")
    public Object validateLoginacct(String loginacct) {
        AjaxResult result = new AjaxResult();
            User user = uService.queryUserByName(loginacct);
            if(user==null) {
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
            }        
        return result;
    }
    
    /**
     * 
     * @Description (添加用户)
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("addDo")
    public Object addDo(User user) {
        AjaxResult result = new AjaxResult();
        try {
            User u = uService.queryUserByName(user.getLoginacct());//判断用户账号是否已存在
            if(u==null) {//用户账后可用
                //所有用户初始密码设为123456，可修改
                user.setUserpswd(MD5Util.digest("123456"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                user.setCreatetime(sdf.format(new Date()));
                uService.addUser(user);
                result.setSuccess(true);
            }else {//用户账号不可用
                result.setData("该账号名已被占用！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (跳往用户编辑页面)
     * @return
     */
    @RequestMapping("goToEdit")
    public String goToEdit(String id,Model model) {
        User user = uService.queryUserById(Integer.parseInt(id));
        model.addAttribute("user",user);
        return "manager/permission/user/edit";
    }
    
    @ResponseBody
    @RequestMapping("editDo")
    public Object editDo(User user) {
        AjaxResult result = new AjaxResult();
        try {
            uService.editUser(user);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (单个删除用户)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            uService.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (批量删除)
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteUsers")
    public Object deleteUsers(String ids) {
        AjaxResult result = new AjaxResult();
        try {
            String userIds = ids.substring(0,ids.length()-1);
            uService.deleteUsers(userIds);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
