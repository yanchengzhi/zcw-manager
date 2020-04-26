package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.TType;

public interface ItemTypeService {

    List<TType> queryTypesPaged(Map<String, Object> map);//��ҳ��ѯ

    int getTypesTotal(Map<String, Object> map);//��ȡ�ܼ�¼����

    TType queryTypeByName(String name);//�����Ʋ�ѯ����

    void addType(TType type);//��ӷ���

    TType queryTypeById(Integer id);//����ID��ѯ����

    void editType(TType type);//�޸ķ���

    void delete(Integer id);//ɾ������

    void deleteTypes(String typeIds);//����ɾ������

}
