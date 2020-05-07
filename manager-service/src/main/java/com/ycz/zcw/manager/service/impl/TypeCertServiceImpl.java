package com.ycz.zcw.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.AccountTypeCertMapper;
import com.ycz.zcw.manager.pojo.AccountTypeCert;
import com.ycz.zcw.manager.service.TypeCertService;

@Service
public class TypeCertServiceImpl implements TypeCertService {
    
    @Autowired
    private AccountTypeCertMapper tcMapper;

    @Override
    public List<AccountTypeCert> getAll() {
        return tcMapper.selectByExample(null);
    }

    @Override
    public void addNew(AccountTypeCert atCert) {
        tcMapper.insertSelective(atCert);
    }

    @Override
    public void removeCert(AccountTypeCert atCert) {
        tcMapper.removeCert(atCert);
    }

}
