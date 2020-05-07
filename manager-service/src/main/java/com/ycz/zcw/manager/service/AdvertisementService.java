package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.Advertisement;

public interface AdvertisementService {

    Advertisement queryAdByName(String name);//按照名称查询广告

    void addAdvertise(Advertisement adver);//添加广告

    int getAdsTotal(Map<String, Object> map);//获取总的记录条数

    List<Advertisement> queryAdsPaged(Map<String, Object> map);//分页查询

    Advertisement queryAdById(Integer id);//按照ID查询

    void editAd(Advertisement adver);//修改广告

    void delete(Integer id);//单条删除

    void deleteAds(String adverIds);//批量删除

}
