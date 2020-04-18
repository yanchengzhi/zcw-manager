package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.memberProjectFollow;
import com.ycz.zcw.manager.pojo.memberProjectFollowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface memberProjectFollowMapper {
    long countByExample(memberProjectFollowExample example);

    int deleteByExample(memberProjectFollowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(memberProjectFollow record);

    int insertSelective(memberProjectFollow record);

    List<memberProjectFollow> selectByExample(memberProjectFollowExample example);

    memberProjectFollow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") memberProjectFollow record, @Param("example") memberProjectFollowExample example);

    int updateByExample(@Param("record") memberProjectFollow record, @Param("example") memberProjectFollowExample example);

    int updateByPrimaryKeySelective(memberProjectFollow record);

    int updateByPrimaryKey(memberProjectFollow record);
}