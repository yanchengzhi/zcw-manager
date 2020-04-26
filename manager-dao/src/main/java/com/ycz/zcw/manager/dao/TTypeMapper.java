package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.TType;
import com.ycz.zcw.manager.pojo.TTypeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TTypeMapper {
    long countByExample(TTypeExample example);

    int deleteByExample(TTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TType record);

    int insertSelective(TType record);

    List<TType> selectByExample(TTypeExample example);

    TType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TType record, @Param("example") TTypeExample example);

    int updateByExample(@Param("record") TType record, @Param("example") TTypeExample example);

    int updateByPrimaryKeySelective(TType record);

    int updateByPrimaryKey(TType record);

    List<TType> queryTypesPaged(Map<String, Object> map);

    int getTypesTotal(Map<String, Object> map);

    @Select("select * from t_type where name=#{name}")
    TType queryTypeByName(String name);

    @Select("select * from t_type where id=#{id}")
    TType queryTypeById(Integer id);

    void deleteTypes(String typeIds);

}