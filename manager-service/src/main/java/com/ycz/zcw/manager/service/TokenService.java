package com.ycz.zcw.manager.service;

import com.ycz.zcw.manager.pojo.UserToken;

public interface TokenService {

    void saveToken(UserToken uToken);//�����û���¼����

    UserToken queryByToken(String token);//�������Ʋ����û�

}
