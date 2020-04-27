package com.ycz.zcw.manager.controller.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.Tag;
import com.ycz.zcw.manager.service.ItemTagService;

/**
 * 
 * @ClassName ItemTagController
 * @Description TODO(项目标签控制器)
 * @author Administrator
 * @Date 2020年4月27日 下午3:50:02
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/tag/")
public class ItemTagController {
    
    @Autowired
    private ItemTagService tagService;
    
    /**
     * 
     * @Description (跳转到项目标签页面)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/itemtag/index";
    }  
    
    /**
     * 
     * @Description (加载树形结构数据)
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAllData")
    public Object loadAllData() {
        List<Tag> topTags = new ArrayList<>();// 存顶级节点的容器
        List<Tag> tags = tagService.getAll();// 获取所有节点
        Map<Integer, Tag> map = new HashMap<>();
        for (Tag t : tags) {
            map.put(t.getId(),t);
        }
        // 父子节点组合
        for (Tag t : tags) {
            if (t.getPid() == 0) {
                topTags.add(t);// 所有顶级节点
            } else {
                Tag parentTag = map.get(t.getPid());// 找出该子节点的顶级父节点
                // 组合父子关系
                parentTag.getChildren().add(t);
            }
        }
        return topTags;
    }
    
    /**
     * 
     * @Description (获取父节点)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getParentTag")
    public Object getParentTag(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            // 查询出要添加菜单的父菜单
            Tag parent = tagService.queryTagById(id);
            result.setData(parent);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (验证标签名是否重复)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateTag")
    public Object validateTag(String name) {
        AjaxResult result = new AjaxResult();
        // 查询名称是否重复
        Tag tag= tagService.queryTagByName(name);
        if (tag == null) {// 不重复
            result.setSuccess(true);
        } else {// 重复
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (添加标签)
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("addTag")
    public Object addTag(Tag tag) {
        AjaxResult result = new AjaxResult();
        try {
            // 判断是否存在
            Tag t = tagService.queryTagByName(tag.getName());
            if (t == null) {// 不存在，可添加
                // 执行添加操作
                tag.setIcon("glyphicon glyphicon-plus");
                tagService.addTag(tag);
                result.setSuccess(true);
            } else {// 重复，不可添加
                result.setData("该标签名已存在！");
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
     * @Description (获取所有一级菜单)
     * @return
     */
    @ResponseBody
    @RequestMapping("getTwoTags")
    public Object getTwoTags() {
        AjaxResult result = new AjaxResult();
        List<Tag> tags = tagService.queryTwoTags();
        result.setData(tags);
        result.setSuccess(true);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("editTag")
    public Object editTag(Tag tag) {
        AjaxResult result = new AjaxResult();
        try {
            tagService.editTag(tag);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("该标签名已被占用！");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("deleteTag")
    public Object deleteTag(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            tagService.deleteTag(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
