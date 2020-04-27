package com.ycz.zcw.manager.controller.permission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ycz.zcw.manager.service.RoleService;

/**
 * 
 * @ClassName RoleController
 * @Description TODO(角色控制器)
 * @author Administrator
 * @Date 2020年4月27日 下午3:49:04
 * @version 1.0.0
 */
@Controller
@RequestMapping("/permission/role/")
public class RoleController {
    
    @Autowired
    private RoleService rService;
    
    /**
     * 
     * @Description (跳往角色维护页面)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/permission/role/index";
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
            List<Role> roles = rService.queryRolesPaged(map);
            //获取总记录条数
            int totalSize = rService.getRolesTotal(map);
            //获取最大页码数
            int maxPage = (totalSize%pageSize==0)?totalSize/pageSize:(totalSize/pageSize)+1;
            //使用分页对象封装数据
            Page<Role> rolePage = new Page<>();
            rolePage.setDatas(roles);
            rolePage.setMaxPage(maxPage);
            rolePage.setPage(page);
            rolePage.setTotalSize(totalSize);
            result.setData(rolePage);//封装到result中返给前台
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (跳往角色添加页面)
     * @return
     */
    @RequestMapping("goToAdd")
    public String goToAdd() {
        return "manager/permission/role/add";
    }
    
    /**
     * 
     * @Description (验证角色是否重复)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateRole")
    public Object validateRole(String name) {
        AjaxResult result = new AjaxResult();
            Role role = rService.queryRoleByName(name);
            if(role==null) {
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
            }        
        return result;
    }
    
    /**
     * 
     * @Description (添加角色操作)
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("addRole")
    public Object addRole(Role role) {
        AjaxResult result = new AjaxResult();
        try {
            Role r = rService.queryRoleByName(role.getName());//判断用户账号是否已存在
            if(r==null) {//角色名可用
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                role.setCreatetime(sdf.format(new Date()));
                rService.addRole(role);
                result.setSuccess(true);
            }else {//角色名不可用
                result.setData("该角色已存在！");
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
     * @Description (跳往角色修改页面)
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("goToEdit")
    public String goToEdit(String id,Model model) {
        Role role = rService.queryRoleById(Integer.parseInt(id));
        model.addAttribute("role",role);
        return "manager/permission/role/edit";
    }
    
    /**
     * 
     * @Description (修改角色)
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("editRole")
    public Object editRole(Role role) {
        AjaxResult result = new AjaxResult();
        try {
            rService.editRole(role);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (单个删除角色)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            rService.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("无法删除！该角色下存在权限！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (批量删除角色)
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteRoles")
    public Object deleteRoles(String ids) {
        AjaxResult result = new AjaxResult();
        try {
            String roleIds = ids.substring(0,ids.length()-1);
            rService.deleteRoles(roleIds);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("doAssign")
    public Object doAssign(Integer roleid,Integer []permissionIds) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("roleid", roleid);
            map.put("permissionIds", permissionIds);
            rService.insertRolePermission(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
