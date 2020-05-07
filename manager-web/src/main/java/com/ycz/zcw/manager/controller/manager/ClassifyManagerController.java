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
 * @Description TODO(分类管理控制器)
 * @author Administrator
 * @Date 2020年4月30日 下午10:48:48
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
     * @Description (跳往分类管理页面)
     * @return
     */
    @RequestMapping("index")
    public String index(Model model) {
        List<BusinessType> bTypes = bService.getAllBusinessTypes();//获取所有商户类型
        List<Cert> certs = cService.getAllCerts();// 查询出所有资质
        model.addAttribute("bTypes", bTypes);
        model.addAttribute("certs",certs);
        // 查出所有资质和账户的对应关系
        List<AccountTypeCert> atCerts = tcService.getAll();
        // 组装一个二维矩阵，一维中存放资质，二维中存放商户类型
        Boolean[][] relations = new Boolean[certs.size()][bTypes.size()];
        for (int i = 0; i < relations.length; i++) {// 先遍历一维
            for (int j = 0; j < relations[i].length; j++) {// 再遍历二维
                // 拿出当前的资质
                Cert cert = certs.get(i);
                // 拿出当前的类型
                BusinessType type = bTypes.get(j);
                // 对照是否有这个资质对应的类型
                for (AccountTypeCert atCert : atCerts) {
                    relations[i][j] = atCert.getAccttype().equals(type.getName()) && atCert.getCertid() == cert.getId();
                    if(relations[i][j]) {
                        break; 
                    }
                }
            }
        }
        //包装好的二维矩阵存到页面中
        model.addAttribute("relations",relations);
        return "manager/servicemanager/classifymanager/index";
    }
    
    /**
     * 
     * @Description (为商户类型添加相应的资质证件)
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
     * @Description (为商户类型减少相应的资质证件)
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
