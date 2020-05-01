package com.ycz.zcw.manager.service;

import com.ycz.zcw.manager.pojo.UserToken;

public interface TokenService {

    void saveToken(UserToken uToken);//保存用户登录令牌

    UserToken queryByToken(String token);//按照令牌查找用户

}
