package com.ycz.zcw.manager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.project.MD5Util;
import com.ycz.project.MyStringUtil;
import com.ycz.zcw.manager.dao.UserDao;
import com.ycz.zcw.manager.dao.UserMapper;
import com.ycz.zcw.manager.dao.UserTokenMapper;
import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.UserExample;
import com.ycz.zcw.manager.pojo.UserExample.Criteria;
import com.ycz.zcw.manager.pojo.UserToken;
import com.ycz.zcw.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper uMapper;
    
    @Autowired
    private UserDao uDao;
    
    @Autowired
    private UserTokenMapper utMapper;

    @Override
    public User queryUser(User user) {
        //查询数据库中是否存在该用户
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andLoginacctEqualTo(user.getLoginacct());//查询登录名
        criteria.andUserpswdEqualTo(MD5Util.digest(user.getUserpswd()));//密码查询
        //返回的是一个List集合，要进行处理
        List <User> list = null;
        try {
            list = uMapper.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
        }
          return list.size()==1?list.get(0):null;
    }

    @Override
    public boolean register(User user) {
        // MD5加密用户密码
        String crypt = MD5Util.digest(user.getUserpswd());
        // 设置密码密文
        user.setUserpswd(crypt);
        user.setUsername(user.getLoginacct());
        user.setCreatetime(MyStringUtil.date2String(new Date()));
        int res = 0;
        try {
            res = uMapper.insertSelective(user);
        } catch (Exception e) {// 发生异常说明用户重复，数据库会报异常
            return false;
        }
        // 判断数据是否保存成功
        return res == 1 ? true : false;
    }

    @Override
    public List<User> getAll() {
        List<User> uList = uMapper.selectByExample(null);
        return uList;
    }

    @Override
    public int getUsersTotal(Map<String, Object> map) {
        return uDao.getUsersTotal(map);
    }

    @Override
    public List<User> queryUsersPaged(Map<String, Object> map) {
        return uDao.queryUsersPaged(map);
    }

    @Override
    public void addUser(User user) {
        uMapper.insertSelective(user);
    }

    @Override
    public User queryUserById(int id) {
        return uMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editUser(User user) {
        uMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(Integer id) {
        uMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User queryUserByName(String loginacct) {
        return uDao.queryUserByName(loginacct);
    }

    @Override
    public void deleteUsers(String userIds) {
        uDao.deleteUsers(userIds);
    }

    @Override
    public List<Integer> queryRoleIdsByUserId(Integer id) {
        return uDao.queryRoleIdsByUserId(id);
    }

    @Override
    public void insertUserRoles(Map<String, Object> map) {
        uDao.insertUserRoles(map);
    }

    @Override
    public void deleteUserRoles(Map<String, Object> map) {
        uDao.deleteUserRoles(map);
    }

    @Override
    public List<Permission> queryPermissionsById(Integer id) {
        return uDao. queryPermissionsById(id);
    }

    @Override
    public boolean sendEmail(String email) {
        //先检查邮箱是否存在
        User u = checkEmail(email);
        if(u!=null) {//邮箱存在，发送邮件
            String tokenStr = UUID.randomUUID().toString().replace("-", "");//生成密码令牌
            //先查询数据库有没有该用户的令牌
            UserToken t = utMapper.queryTokenById(u.getId());
            if(t!=null) {//存在令牌
                //设置密码令牌
                t.setPasswordToken(tokenStr);
                //然后更新
                utMapper.updateByPrimaryKeySelective(t);
            }else {//不存在令牌
                UserToken tok = new UserToken();
                tok.setUserId(u.getId());
                tok.setPasswordToken(tokenStr);
                //插入新的令牌
                utMapper.insertSelective(tok);
            }
            //将连接带令牌的链接发给用户
            HtmlEmail hEmail = new HtmlEmail();
            hEmail.setHostName("smtp.126.com");//设置主机名
            hEmail.setSmtpPort(25);//设置端口号
            hEmail.setAuthentication("yan_cheng_zhi@126.com", "DCGXPXXURQMEHNAG");//设置连接账号
            try {
                Email to = hEmail.addTo(email);//设置收件人
                hEmail.setFrom("yan_cheng_zhi@126.com");//发件人
                hEmail.setSubject("找回密码");//邮件标题
                //设置邮件内容
                hEmail.setContent("<h3>半小时内点击链接重置密码</h3><a href='http://121.42.230.150:3000/manager-web/resetPass?token="+tokenStr+"'>重置密码</a>","text/html;charset=utf-8");
                hEmail.send();//发送邮件
            } catch (EmailException e) {
                System.out.println("邮件发送失败！");
                e.printStackTrace();
            }
            return true;
        }else {//邮箱不存在
            return false;
        }
    }

    @Override
    public User checkEmail(String email) {
        return uDao.checkEmail(email);
    }

    @Override
    public int updateUserPass(String password,User user) {
        //密码进行MD5加密
        String pwd = MD5Util.digest(password);
        user.setUserpswd(pwd);
        return uMapper.updateByPrimaryKeySelective(user);
    }


}
