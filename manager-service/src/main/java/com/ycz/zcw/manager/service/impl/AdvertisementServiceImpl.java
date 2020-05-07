package com.ycz.zcw.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.AdvertisementMapper;
import com.ycz.zcw.manager.pojo.Advertisement;
import com.ycz.zcw.manager.service.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    
    @Autowired
    private AdvertisementMapper aMapper;

    @Override
    public Advertisement queryAdByName(String name) {
        return aMapper.queryAdByName(name);
    }

    @Override
    public void addAdvertise(Advertisement adver) {
        aMapper.insertSelective(adver);
    }

    @Override
    public int getAdsTotal(Map<String, Object> map) {
        return aMapper.getAdsTotal(map);
    }

    @Override
    public List<Advertisement> queryAdsPaged(Map<String, Object> map) {
        return aMapper.queryAdsPaged(map);
    }

    @Override
    public Advertisement queryAdById(Integer id) {
        return aMapper.queryAdById(id);
    }

    @Override
    public void editAd(Advertisement adver) {
        aMapper.updateByPrimaryKeySelective(adver);
    }

    @Override
    public void delete(Integer id) {
        aMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteAds(String adverIds) {
        aMapper.deleteAds(adverIds);
    }

}
