package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.BusinessType;
import com.ycz.zcw.manager.pojo.BusinessTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessTypeMapper {
    long countByExample(BusinessTypeExample example);

    int deleteByExample(BusinessTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BusinessType record);

    int insertSelective(BusinessType record);

    List<BusinessType> selectByExample(BusinessTypeExample example);

    BusinessType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BusinessType record, @Param("example") BusinessTypeExample example);

    int updateByExample(@Param("record") BusinessType record, @Param("example") BusinessTypeExample example);

    int updateByPrimaryKeySelective(BusinessType record);

    int updateByPrimaryKey(BusinessType record);
}