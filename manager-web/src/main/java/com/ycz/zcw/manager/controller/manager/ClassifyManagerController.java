package com.ycz.zcw.manager.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.zcw.manager.pojo.AccountTypeCert;
import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.BusinessType;
import com.ycz.zcw.manager.pojo.Cert;
import com.ycz.zcw.manager.service.BusinessTypeService;
import com.ycz.zcw.manager.service.CertMaintainService;
import com.ycz.zcw.manager.service.TypeCertService;

/**
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

    @Autowired
    private TypeCertService tcService;
    
    @Autowired
    private BusinessTypeService bService;

    /**
     * @Description (�����������ҳ��)
     * @return
     */
    @RequestMapping("index")
    public String index(Model model) {
        List<BusinessType> bTypes = bService.getAllBusinessTypes();//��ȡ�����̻�����
        List<Cert> certs = cService.getAllCerts();// ��ѯ����������
        model.addAttribute("bTypes", bTypes);
        model.addAttribute("certs",certs);
        // ����������ʺ��˻��Ķ�Ӧ��ϵ
        List<AccountTypeCert> atCerts = tcService.getAll();
        // ��װһ����ά����һά�д�����ʣ���ά�д���̻�����
        Boolean[][] relations = new Boolean[certs.size()][bTypes.size()];
        for (int i = 0; i < relations.length; i++) {// �ȱ���һά
            for (int j = 0; j < relations[i].length; j++) {// �ٱ�����ά
                // �ó���ǰ������
                Cert cert = certs.get(i);
                // �ó���ǰ������
                BusinessType type = bTypes.get(j);
                // �����Ƿ���������ʶ�Ӧ������
                for (AccountTypeCert atCert : atCerts) {
                    relations[i][j] = atCert.getAccttype().equals(type.getName()) && atCert.getCertid() == cert.getId();
                    if(relations[i][j]) {
                        break; 
                    }
                }
            }
        }
        //��װ�õĶ�ά����浽ҳ����
        model.addAttribute("relations",relations);
        return "manager/servicemanager/classifymanager/index";
    }
    
    /**
     * 
     * @Description (Ϊ�̻����������Ӧ������֤��)
     * @param atCert
     * @return
     */
    @ResponseBody
    @RequestMapping("addBusinessCert")
    public Object addBusinessCert(AccountTypeCert atCert) {
        AjaxResult result = new AjaxResult();
        try {
            tcService.addNew(atCert);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (Ϊ�̻����ͼ�����Ӧ������֤��)
     * @param atCert
     * @return
     */
    @ResponseBody
    @RequestMapping("removeBusinessCert")
    public Object removeBusinessCert(AccountTypeCert atCert) {
        AjaxResult result = new AjaxResult();
        try {
            tcService.removeCert(atCert);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
