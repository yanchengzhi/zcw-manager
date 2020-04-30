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
 * @Description TODO(����ά��������)
 * @author Administrator
 * @Date 2020��4��30�� ����10:36:03
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/cert/")
public class CertMaintainController {
    
    @Autowired
    private CertMaintainService cService;
    
    /**
     * 
     * @Description����ת������ά��ҳ�棩
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/certmaintain/index";
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
            List<Cert> certs = cService.queryCertsPaged(map);
            // ��ȡ�ܼ�¼����
            int totalSize = cService.getCertsTotal(map);
            // ��ȡ���ҳ����
            int maxPage = (totalSize % pageSize == 0) ? totalSize / pageSize : (totalSize / pageSize) + 1;
            // ʹ�÷�ҳ�����װ����
            Page<Cert> certPage = new Page<>();
            certPage.setDatas(certs);
            certPage.setMaxPage(maxPage);
            certPage.setPage(page);
            certPage.setTotalSize(totalSize);
            result.setData(certPage);// ��װ��result�з���ǰ̨
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (��֤�����Ƿ��ظ�)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateCert")
    public Object validateCert(String name) {
        AjaxResult result = new AjaxResult();
        Cert cert = cService.queryCertByName(name);// ��ѯ�Ƿ��ظ�
        if (cert == null) {// ����
            result.setSuccess(true);
        } else {// �ظ�
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�������)
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
                result.setData("�������Ѵ��ڣ�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
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
     * @Description (�޸�����)
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
     * @Description (����ɾ��)
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
     * @Description (����ɾ��)
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
