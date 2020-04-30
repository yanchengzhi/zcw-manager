package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.Cert;
import com.ycz.zcw.manager.pojo.CertExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CertMapper {
    long countByExample(CertExample example);

    int deleteByExample(CertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    int insertSelective(Cert record);

    List<Cert> selectByExample(CertExample example);

    Cert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cert record, @Param("example") CertExample example);

    int updateByExample(@Param("record") Cert record, @Param("example") CertExample example);

    int updateByPrimaryKeySelective(Cert record);

    int updateByPrimaryKey(Cert record);

    List<Cert> queryCertsPaged(Map<String, Object> map);

    int getCertsTotal(Map<String, Object> map);

    @Select("select * from t_cert where name=#{name}")
    Cert queryCertByName(String name);

    @Select("select * from t_cert where id=#{id}")
    Cert queryCertById(Integer id);

    void deleteCerts(String certIds);

    @Select("select * from t_cert order by id")
    List<Cert> getAllCerts();
}