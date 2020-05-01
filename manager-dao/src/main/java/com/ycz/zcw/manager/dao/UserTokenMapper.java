package com.ycz.zcw.manager.dao;

import com.ycz.zcw.manager.pojo.UserToken;
import com.ycz.zcw.manager.pojo.UserTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserTokenMapper {
    long countByExample(UserTokenExample example);

    int deleteByExample(UserTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    List<UserToken> selectByExample(UserTokenExample example);

    UserToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserToken record, @Param("example") UserTokenExample example);

    int updateByExample(@Param("record") UserToken record, @Param("example") UserTokenExample example);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);

    @Select("select * from t_user_token where user_id=#{userid}")
    UserToken queryTokenById(Integer userid);

    @Select("select * from t_user_token where password_token=#{token}")
    UserToken queryByToken(String token);
}