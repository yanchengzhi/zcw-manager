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
    
    /**
     * 
     * @Description (�û���¼)
     * @param user
     * @param session
     * @return
     */
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
    
    /**
     * 
     * @Description (�����û�ҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/user/index";
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ)
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
            //��ѯ��Ҫ�ļ�¼
            List<User> users = uService.queryUsersPaged(map);
            //��ȡ�ܼ�¼����
            int totalSize = uService.getUsersTotal(map);
            //��ȡ���ҳ����
            int maxPage = (totalSize%pageSize==0)?totalSize/pageSize:(totalSize/pageSize)+1;
            //ʹ�÷�ҳ�����װ����
            Page<User> userPage = new Page<>();
            userPage.setDatas(users);
            userPage.setMaxPage(maxPage);
            userPage.setPage(page);
            userPage.setTotalSize(totalSize);
            result.setData(userPage);//��װ��result�з���ǰ̨
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�����û����ҳ��)
     * @return
     */
    @RequestMapping("goToAdd")
    public String goToAdd() {
        return "manager/permission/user/add";
    }
    
    /**
     * 
     * @Description (��֤�˺��Ƿ��ظ�)
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
     * @Description (����û�)
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("addDo")
    public Object addDo(User user) {
        AjaxResult result = new AjaxResult();
        try {
            User u = uService.queryUserByName(user.getLoginacct());//�ж��û��˺��Ƿ��Ѵ���
            if(u==null) {//�û��˺����
                //�����û���ʼ������Ϊ123456�����޸�
                user.setUserpswd(MD5Util.digest("123456"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                user.setCreatetime(sdf.format(new Date()));
                uService.addUser(user);
                result.setSuccess(true);
            }else {//�û��˺Ų�����
                result.setData("���˺����ѱ�ռ�ã�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�����û��༭ҳ��)
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
     * @Description (����ɾ���û�)
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
     * @Description (����ɾ��)
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
