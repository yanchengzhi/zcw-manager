package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.Advertisement;
import com.ycz.zcw.manager.pojo.AdvertisementExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdvertisementMapper {
    long countByExample(AdvertisementExample example);

    int deleteByExample(AdvertisementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    List<Advertisement> selectByExample(AdvertisementExample example);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByExample(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

    @Select("select * from t_advertisement where name=#{name}")
    Advertisement queryAdByName(String name);

    int getAdsTotal(Map<String, Object> map);

    List<Advertisement> queryAdsPaged(Map<String, Object> map);

    @Select("select * from t_advertisement where id=#{id}")
    Advertisement queryAdById(Integer id);

    void deleteAds(String adverIds);
}