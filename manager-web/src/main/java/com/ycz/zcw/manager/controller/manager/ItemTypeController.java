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
 * @Description TODO(��Ŀ���������)
 * @author Administrator
 * @Date 2020��4��26�� ����9:09:56
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/type/")
public class ItemTypeController {

    @Autowired
    private ItemTypeService itService;

    /**
     * @Description (������Ŀ����ҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/itemclassify/index";
    }

    /**
     * @Description (���첽��ҳ��ѯ)
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
            // ��ѯ��Ҫ�ļ�¼
            List<TType> types = itService.queryTypesPaged(map);
            // ��ȡ�ܼ�¼����
            int totalSize = itService.getTypesTotal(map);
            // ��ȡ���ҳ����
            int maxPage = (totalSize % pageSize == 0) ? totalSize / pageSize : (totalSize / pageSize) + 1;
            // ʹ�÷�ҳ�����װ����
            Page<TType> typePage = new Page<>();
            typePage.setDatas(types);
            typePage.setMaxPage(maxPage);
            typePage.setPage(page);
            typePage.setTotalSize(totalSize);
            result.setData(typePage);// ��װ��result�з���ǰ̨
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (�첽������֤���������Ƿ��ظ�)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateType")
    public Object validateType(String name) {
        AjaxResult result = new AjaxResult();
        TType type = itService.queryTypeByName(name);// ��ѯ�Ƿ��ظ�
        if (type == null) {// ����
            result.setSuccess(true);
        } else {// �ظ�
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @Description (��ӷ���)
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
                result.setData("�÷����Ѵ��ڣ�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
        }
        return result;
    }

    /**
     * @Description (��ȡ�޸ĵĶ��󣬴���ǰ̨)
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
     * @Description (�޸ķ���)
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
     * @Description (����ɾ��)
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
     * @Description (����ɾ��)
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
