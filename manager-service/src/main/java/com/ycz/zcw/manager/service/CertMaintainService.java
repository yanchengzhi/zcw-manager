package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Cert;

public interface CertMaintainService {

    List<Cert> queryCertsPaged(Map<String, Object> map);//��ҳ��ѯ

    int getCertsTotal(Map<String, Object> map);//��ȡ�ܼ�¼����

    Cert queryCertByName(String name);//�������Ʋ�������

    void addCert(Cert cert);//�������

    Cert queryCertById(Integer id);//����ID��ѯ����

    void editCert(Cert cert);//�޸�����

    void delete(Integer id);//����ɾ��

    void deleteCerts(String certIds);//����ɾ��

    List<Cert> getAllCerts();//��ѯ��������

}
