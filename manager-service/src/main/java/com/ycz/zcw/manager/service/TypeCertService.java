package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.AccountTypeCert;

public interface TypeCertService {

    List<AccountTypeCert> getAll();//查询所有记录

    void addNew(AccountTypeCert atCert);//添加新记录

    void removeCert(AccountTypeCert atCert);//删除记录

}
