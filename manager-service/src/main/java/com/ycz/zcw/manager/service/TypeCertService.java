package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.AccountTypeCert;

public interface TypeCertService {

    List<AccountTypeCert> getAll();//��ѯ���м�¼

    void addNew(AccountTypeCert atCert);//����¼�¼

    void removeCert(AccountTypeCert atCert);//ɾ����¼

}
