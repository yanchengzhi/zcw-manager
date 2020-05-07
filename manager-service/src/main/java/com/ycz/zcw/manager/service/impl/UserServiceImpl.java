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
        //��ѯ���ݿ����Ƿ���ڸ��û�
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        //���ò�ѯ����
        criteria.andLoginacctEqualTo(user.getLoginacct());//��ѯ��¼��
        criteria.andUserpswdEqualTo(MD5Util.digest(user.getUserpswd()));//�����ѯ
        //���ص���һ��List���ϣ�Ҫ���д���
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
        // MD5�����û�����
        String crypt = MD5Util.digest(user.getUserpswd());
        // ������������
        user.setUserpswd(crypt);
        user.setUsername(user.getLoginacct());
        user.setCreatetime(MyStringUtil.date2String(new Date()));
        int res = 0;
        try {
            res = uMapper.insertSelective(user);
        } catch (Exception e) {// �����쳣˵���û��ظ������ݿ�ᱨ�쳣
            return false;
        }
        // �ж������Ƿ񱣴�ɹ�
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
        //�ȼ�������Ƿ����
        User u = checkEmail(email);
        if(u!=null) {//������ڣ������ʼ�
            String tokenStr = UUID.randomUUID().toString().replace("-", "");//������������
            //�Ȳ�ѯ���ݿ���û�и��û�������
            UserToken t = utMapper.queryTokenById(u.getId());
            if(t!=null) {//��������
                //������������
                t.setPasswordToken(tokenStr);
                //Ȼ�����
                utMapper.updateByPrimaryKeySelective(t);
            }else {//����������
                UserToken tok = new UserToken();
                tok.setUserId(u.getId());
                tok.setPasswordToken(tokenStr);
                //�����µ�����
                utMapper.insertSelective(tok);
            }
            //�����Ӵ����Ƶ����ӷ����û�
            HtmlEmail hEmail = new HtmlEmail();
            hEmail.setHostName("smtp.126.com");//����������
            hEmail.setSmtpPort(25);//���ö˿ں�
            hEmail.setAuthentication("yan_cheng_zhi@126.com", "DCGXPXXURQMEHNAG");//���������˺�
            try {
                Email to = hEmail.addTo(email);//�����ռ���
                hEmail.setFrom("yan_cheng_zhi@126.com");//������
                hEmail.setSubject("�һ�����");//�ʼ�����
                //�����ʼ�����
                hEmail.setContent("<h3>��Сʱ�ڵ��������������</h3><a href='http://121.42.230.150:3000/manager-web/resetPass?token="+tokenStr+"'>��������</a>","text/html;charset=utf-8");
                hEmail.send();//�����ʼ�
            } catch (EmailException e) {
                System.out.println("�ʼ�����ʧ�ܣ�");
                e.printStackTrace();
            }
            return true;
        }else {//���䲻����
            return false;
        }
    }

    @Override
    public User checkEmail(String email) {
        return uDao.checkEmail(email);
    }

    @Override
    public int updateUserPass(String password,User user) {
        //�������MD5����
        String pwd = MD5Util.digest(password);
        user.setUserpswd(pwd);
        return uMapper.updateByPrimaryKeySelective(user);
    }


}
