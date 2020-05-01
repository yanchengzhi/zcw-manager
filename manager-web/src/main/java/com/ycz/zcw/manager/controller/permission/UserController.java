package com.ycz.zcw.manager.controller.permission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
import com.ycz.zcw.manager.pojo.Role;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.UserToken;
import com.ycz.zcw.manager.pojo.constants.Constants;
import com.ycz.zcw.manager.service.RoleService;
import com.ycz.zcw.manager.service.TokenService;
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
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private TokenService tService;
    
    /**
     * 
     * @Description (退出登录)
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();//会话域失效
        return "redirect:/login.jsp";//重定向到登录页面
    }
    
    
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
    public String login(User user,HttpSession session,HttpServletResponse response,
            @RequestParam(value="rememer",defaultValue = "0")String remember) {
        User dbUser = uService.queryUser(user);
        if(dbUser==null) {//登录失败时
            session.setAttribute("errorUser", user);
            session.setAttribute("msg", "登陆失败！");
            //重定向到登录页面
            return "redirect:/login.jsp";
        }else {//登录成功时
            session.setAttribute(Constants.LOGIN_USER, dbUser);
            //判断用户是否使用了记住我功能
            if(remember.equals("1")) {//如果使用了
                //生成一个用来做自动登录功能的的token令牌，数据库1份，用来做比较，浏览器1份（以cookie的形式永久保存）
                //浏览器访问的时候有可能会带上，根据这个令牌对用户进行匹配
                String token = UUID.randomUUID().toString().replace("-","");
                //保存令牌到数据库中，令牌与用户是绑定的
                UserToken uToken = new UserToken();
                uToken.setUserId(dbUser.getId());
                uToken.setAutoLoginToken(token);
                //执行保存
                tService.saveToken(uToken);
                //以cookie的形式保存到浏览器中（通过响应对象）
                Cookie cookie = new Cookie("autoLogin",token);//新建Cookie对象
                cookie.setMaxAge(3600 * 24 * 7);//设置cookie的有效时间，默认是秒为单位的，这里设置有效期限为7天
                //注意，在springMvc中，cookie必须是当前项目下的路径，默认的是"/"，需要修改路径
                cookie.setPath(session.getServletContext().getContextPath());//设置cookie的路径
                response.addCookie(cookie);//将cookie添加到响应中
                //将登陆的用户放在缓存库中，模拟Redis缓存,避免了去数据库检索，数据库检索是一个耗时操作
                //先去缓存库中查，查不到再去数据库中查
                session.getServletContext().setAttribute(token, dbUser);
            }
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
            result.setData("无法删除！该用户下存在角色！");
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
    
    /**
     * 
     * @Description (跳往角色分配页面)
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("goAssign")
    public String goAssign(Integer id,Model model){
        //查询要分配角色的用户
        User user = uService.queryUserById(id);
        model.addAttribute("user",user);
        //查询系统的所有的角色
        List<Role> roles = rService.getAllRoles();
        List<Role> assignRoles = new ArrayList<>();//存已分配角色的容器
        List<Role> unAssignRoles = new ArrayList<>();//存未分配角色的容器
        //获取用户已分配所有角色的id
        List<Integer> roleIds = uService.queryRoleIdsByUserId(id);
        for(Role r:roles) {
            if(roleIds.contains(r.getId())) {
                assignRoles.add(r);
            }else {
                unAssignRoles.add(r);
            }
        }
        //将已分配的角色和未分配的角色存到页面中
        model.addAttribute("assignRoles",assignRoles);
        model.addAttribute("unAssignRoles",unAssignRoles);
        return "manager/permission/user/assign_role";
    }
    
    /**
     * 
     * @Description (为用户分配角色)
     * @param userid 用户ID
     * @param assignIds 要分配的角色ID 
     * @return
     */
    @ResponseBody
    @RequestMapping("assignRoles")
    public Object assignRoles(Integer userid,Integer []assignIds) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("userid", userid);
            map.put("assignIds", assignIds);
            uService.insertUserRoles(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (取消用户的角色)
     * @param userid
     * @param unAssignIds
     * @return
     */
    @ResponseBody
    @RequestMapping("cancelRoles")
    public Object cancelRoles(Integer userid,Integer []unAssignIds) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("userid", userid);
            map.put("unAssignIds", unAssignIds);
            uService.deleteUserRoles(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
