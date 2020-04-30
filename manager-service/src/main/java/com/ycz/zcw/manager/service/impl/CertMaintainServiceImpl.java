package com.ycz.zcw.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.CertMapper;
import com.ycz.zcw.manager.pojo.Cert;
import com.ycz.zcw.manager.service.CertMaintainService;

@Service
public class CertMaintainServiceImpl implements CertMaintainService {
    
    @Autowired
    private CertMapper cMapper;

    @Override
    public List<Cert> queryCertsPaged(Map<String, Object> map) {
        return cMapper.queryCertsPaged(map);
    }

    @Override
    public int getCertsTotal(Map<String, Object> map) {
        return cMapper.getCertsTotal(map);
    }

    @Override
    public Cert queryCertByName(String name) {
        return cMapper.queryCertByName(name);
    }

    @Override
    public void addCert(Cert cert) {
      cMapper.insertSelective(cert); 
    }

    @Override
    public Cert queryCertById(Integer id) {
        return cMapper.queryCertById(id);
    }

    @Override
    public void editCert(Cert cert) {
        cMapper.updateByPrimaryKeySelective(cert);
    }

    @Override
    public void delete(Integer id) {
        cMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteCerts(String certIds) {
        cMapper.deleteCerts(certIds);
    }

    @Override
    public List<Cert> getAllCerts(){
        return cMapper.getAllCerts();
    }

}
