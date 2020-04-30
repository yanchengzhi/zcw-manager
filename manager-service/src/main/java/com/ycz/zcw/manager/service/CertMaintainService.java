package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Cert;

public interface CertMaintainService {

    List<Cert> queryCertsPaged(Map<String, Object> map);//分页查询

    int getCertsTotal(Map<String, Object> map);//获取总记录条数

    Cert queryCertByName(String name);//按照名称查找资质

    void addCert(Cert cert);//添加资质

    Cert queryCertById(Integer id);//按照ID查询资质

    void editCert(Cert cert);//修改资质

    void delete(Integer id);//单个删除

    void deleteCerts(String certIds);//批量删除

    List<Cert> getAllCerts();//查询所有资质

}
