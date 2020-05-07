package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.AccountTypeCert;
import com.ycz.zcw.manager.pojo.AccountTypeCertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountTypeCertMapper {
    long countByExample(AccountTypeCertExample example);

    int deleteByExample(AccountTypeCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    int insertSelective(AccountTypeCert record);

    List<AccountTypeCert> selectByExample(AccountTypeCertExample example);

    AccountTypeCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountTypeCert record, @Param("example") AccountTypeCertExample example);

    int updateByExample(@Param("record") AccountTypeCert record, @Param("example") AccountTypeCertExample example);

    int updateByPrimaryKeySelective(AccountTypeCert record);

    int updateByPrimaryKey(AccountTypeCert record);

    @Select("delete from t_account_type_cert where accttype=#{accttype} and certid=#{certid}")
    void removeCert(AccountTypeCert atCert);
}