package com.ycz.zcw.manager.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Cert;
import com.ycz.zcw.manager.service.CertMaintainService;

/**
 * 
 * @ClassName ClassifyManagerController
 * @Description TODO(������������)
 * @author Administrator
 * @Date 2020��4��30�� ����10:48:48
 * @version 1.0.0
 */
@Controller
@RequestMapping("manager/classifymanager/")
public class ClassifyManagerController {
    
    @Autowired
    private CertMaintainService cService;
    
    /**
     * 
     * @Description (�����������ҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/classifymanager/index";
    }
    
    /**
     * 
     * @Description (��ѯ�������ʣ�����ҳ)
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Object list() {
        AjaxResult result = new AjaxResult();
        try {
            //��ѯ��������֤��
            List<Cert> certs = cService.getAllCerts();
            result.setData(certs);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;     
    }

}
