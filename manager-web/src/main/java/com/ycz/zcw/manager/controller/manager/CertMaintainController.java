package com.ycz.zcw.manager.controller.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Cert;
import com.ycz.zcw.manager.pojo.Page;
import com.ycz.zcw.manager.service.CertMaintainService;

/**
 * 
 * @ClassName CertMaintainController
 * @Description TODO(资质维护控制器)
 * @author Administrator
 * @Date 2020年4月30日 下午10:36:03
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/cert/")
public class CertMaintainController {
    
    @Autowired
    private CertMaintainService cService;
    
    /**
     * 
     * @Description（跳转到资质维护页面）
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/certmaintain/index";
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
            List<Cert> certs = cService.queryCertsPaged(map);
            // 获取总记录条数
            int totalSize = cService.getCertsTotal(map);
            // 获取最大页码数
            int maxPage = (totalSize % pageSize == 0) ? totalSize / pageSize : (totalSize / pageSize) + 1;
            // 使用分页对象封装数据
            Page<Cert> certPage = new Page<>();
            certPage.setDatas(certs);
            certPage.setMaxPage(maxPage);
            certPage.setPage(page);
            certPage.setTotalSize(totalSize);
            result.setData(certPage);// 封装到result中返给前台
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (验证资质是否重复)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateCert")
    public Object validateCert(String name) {
        AjaxResult result = new AjaxResult();
        Cert cert = cService.queryCertByName(name);// 查询是否重复
        if (cert == null) {// 可用
            result.setSuccess(true);
        } else {// 重复
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (添加资质)
     * @param cert
     * @return
     */
    @ResponseBody
    @RequestMapping("addCert")
    public Object addCert(Cert cert) {
        AjaxResult result = new AjaxResult();
        try {
            Cert c = cService.queryCertByName(cert.getName());
            if (c == null) {
                cService.addCert(cert);
                result.setSuccess(true);
            } else {
                result.setData("该资质已存在！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("getCert")
    public Object getCert(Integer id) {
        AjaxResult result = new AjaxResult();
        Cert cert = cService.queryCertById(id);
        result.setData(cert);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 
     * @Description (修改资质)
     * @param cert
     * @return
     */
    @ResponseBody
    @RequestMapping("editCert")
    public Object editCert(Cert cert) {
        AjaxResult result = new AjaxResult();
        try {
            cService.editCert(cert);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (单个删除)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            cService.delete(id);
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
    @RequestMapping("deleteCerts")
    public Object deleteCerts(String ids) {
        AjaxResult result = new AjaxResult();
        try {
            String certIds = ids.substring(0, ids.length() - 1);
            cService.deleteCerts(certIds);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
