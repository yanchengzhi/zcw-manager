package com.ycz.zcw.manager.controller.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Page;
import com.ycz.zcw.manager.pojo.TType;
import com.ycz.zcw.manager.service.ItemTypeService;

/**
 * @ClassName ItemClassifyController
 * @Description TODO(项目分类控制器)
 * @author Administrator
 * @Date 2020年4月26日 下午9:09:56
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/type/")
public class ItemTypeController {

    @Autowired
    private ItemTypeService itService;

    /**
     * @Description (跳往项目分类页面)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/itemclassify/index";
    }

    /**
     * @Description (作异步分页查询)
     * @param page
     * @param pageSize
     * @param queryText
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Object list(@RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "queryText", required = false) String queryText) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("queryText", queryText);
            // 查询需要的记录
            List<TType> types = itService.queryTypesPaged(map);
            // 获取总记录条数
            int totalSize = itService.getTypesTotal(map);
            // 获取最大页码数
            int maxPage = (totalSize % pageSize == 0) ? totalSize / pageSize : (totalSize / pageSize) + 1;
            // 使用分页对象封装数据
            Page<TType> typePage = new Page<>();
            typePage.setDatas(types);
            typePage.setMaxPage(maxPage);
            typePage.setPage(page);
            typePage.setTotalSize(totalSize);
            result.setData(typePage);// 封装到result中返给前台
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (异步请求验证分类名称是否重复)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateType")
    public Object validateType(String name) {
        AjaxResult result = new AjaxResult();
        TType type = itService.queryTypeByName(name);// 查询是否重复
        if (type == null) {// 可用
            result.setSuccess(true);
        } else {// 重复
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (添加分类)
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("addType")
    public Object addType(TType type) {
        AjaxResult result = new AjaxResult();
        try {
            TType t = itService.queryTypeByName(type.getName());
            if (t == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                type.setCreatetime(sdf.format(new Date()));
                itService.addType(type);
                result.setSuccess(true);
            } else {
                result.setData("该分类已存在！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
        }
        return result;
    }

    /**
     * @Description (获取修改的对象，传给前台)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getType")
    public Object getType(Integer id) {
        AjaxResult result = new AjaxResult();
        TType type = itService.queryTypeById(id);
        result.setData(type);
        result.setSuccess(true);
        return result;
    }

    /**
     * @Description (修改分类)
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("editType")
    public Object editType(TType type) {
        AjaxResult result = new AjaxResult();
        try {
            itService.editType(type);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (单个删除)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            itService.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (批量删除)
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteTypes")
    public Object deleteTypes(String ids) {
        AjaxResult result = new AjaxResult();
        try {
            String typeIds = ids.substring(0, ids.length() - 1);
            itService.deleteTypes(typeIds);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
