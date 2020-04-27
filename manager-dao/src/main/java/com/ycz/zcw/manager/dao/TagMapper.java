package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.Tag;
import com.ycz.zcw.manager.pojo.TagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TagMapper {
    long countByExample(TagExample example);

    int deleteByExample(TagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    List<Tag> selectByExample(TagExample example);

    Tag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tag record, @Param("example") TagExample example);

    int updateByExample(@Param("record") Tag record, @Param("example") TagExample example);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    @Select("select * from t_tag where id=#{id}")
    Tag queryTagById(Integer id);

    @Select("select * from t_tag where name=#{name}")
    Tag queryTagByName(String name);

    @Select("select * from t_tag where pid=1")
    List<Tag> queryTwoTags();
}