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
 * @Description TODO(��Ŀ��ǩ������)
 * @author Administrator
 * @Date 2020��4��27�� ����3:50:02
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/tag/")
public class ItemTagController {
    
    @Autowired
    private ItemTagService tagService;
    
    /**
     * 
     * @Description (��ת����Ŀ��ǩҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/itemtag/index";
    }  
    
    /**
     * 
     * @Description (�������νṹ����)
     * @return
     */
    @ResponseBody
    @RequestMapping("loadAllData")
    public Object loadAllData() {
        List<Tag> topTags = new ArrayList<>();// �涥���ڵ������
        List<Tag> tags = tagService.getAll();// ��ȡ���нڵ�
        Map<Integer, Tag> map = new HashMap<>();
        for (Tag t : tags) {
            map.put(t.getId(),t);
        }
        // ���ӽڵ����
        for (Tag t : tags) {
            if (t.getPid() == 0) {
                topTags.add(t);// ���ж����ڵ�
            } else {
                Tag parentTag = map.get(t.getPid());// �ҳ����ӽڵ�Ķ������ڵ�
                // ��ϸ��ӹ�ϵ
                parentTag.getChildren().add(t);
            }
        }
        return topTags;
    }
    
    /**
     * 
     * @Description (��ȡ���ڵ�)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getParentTag")
    public Object getParentTag(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            // ��ѯ��Ҫ��Ӳ˵��ĸ��˵�
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
     * @Description (��֤��ǩ���Ƿ��ظ�)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateTag")
    public Object validateTag(String name) {
        AjaxResult result = new AjaxResult();
        // ��ѯ�����Ƿ��ظ�
        Tag tag= tagService.queryTagByName(name);
        if (tag == null) {// ���ظ�
            result.setSuccess(true);
        } else {// �ظ�
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (��ӱ�ǩ)
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("addTag")
    public Object addTag(Tag tag) {
        AjaxResult result = new AjaxResult();
        try {
            // �ж��Ƿ����
            Tag t = tagService.queryTagByName(tag.getName());
            if (t == null) {// �����ڣ������
                // ִ����Ӳ���
                tag.setIcon("glyphicon glyphicon-plus");
                tagService.addTag(tag);
                result.setSuccess(true);
            } else {// �ظ����������
                result.setData("�ñ�ǩ���Ѵ��ڣ�");
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
     * @Description (��ȡ����һ���˵�)
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
            result.setData("�ñ�ǩ���ѱ�ռ�ã�");
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
