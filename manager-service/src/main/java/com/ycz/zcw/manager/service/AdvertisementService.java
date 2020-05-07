package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Advertisement;

public interface AdvertisementService {

    Advertisement queryAdByName(String name);//�������Ʋ�ѯ���

    void addAdvertise(Advertisement adver);//��ӹ��

    int getAdsTotal(Map<String, Object> map);//��ȡ�ܵļ�¼����

    List<Advertisement> queryAdsPaged(Map<String, Object> map);//��ҳ��ѯ

    Advertisement queryAdById(Integer id);//����ID��ѯ

    void editAd(Advertisement adver);//�޸Ĺ��

    void delete(Integer id);//����ɾ��

    void deleteAds(String adverIds);//����ɾ��

}
