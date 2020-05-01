package com.ycz.zcw.manager.pojo;

public class UserToken {
    private Integer id;

    private Integer userId;

    private String passwordToken;

    private String autoLoginToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken == null ? null : passwordToken.trim();
    }

    public String getAutoLoginToken() {
        return autoLoginToken;
    }

    public void setAutoLoginToken(String autoLoginToken) {
        this.autoLoginToken = autoLoginToken == null ? null : autoLoginToken.trim();
    }
}