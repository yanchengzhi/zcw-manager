package com.ycz.zcw.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.PermissionDao;
import com.ycz.zcw.manager.dao.PermissionMapper;
import com.ycz.zcw.manager.pojo.Permission;
import com.ycz.zcw.manager.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
    
    @Autowired
    private PermissionMapper pMapper;
    
    @Autowired
    private PermissionDao pDao;

    @Override
    public List<Permission> getAllMenus() {
        //��ѯ���в˵�
        List<Permission> menus = pMapper.selectByExample(null);
        List<Permission> pMenus = new ArrayList<>();//�������˵�
        Map<Integer, Permission> map = new HashMap<>();
        //�����в˵���ID��Ϊ�����˵���Ϊֵ�浽map������
        for(Permission p:menus) {
            map.put(p.getId(), p);
        }
        for(Permission p:menus) {
            if(p.getPid()==1) {//�ҳ����еĸ��˵�����ӵ�List��
                pMenus.add(p);
            }else if(p.getPid()!=0){//�ҳ������Ӳ˵����ӵ���Ӧ�ĸ��˵���
                Integer pId = p.getPid();//��ȡ�Ӳ˵���pId
                Permission permission = map.get(pId);//����pId��ȡ���˵�
                //��ȡ���˵��������Ӳ˵�
                List<Permission> cMenus = permission.getChildren();
                if(cMenus!=null) {//��ǰ���Ӳ˵�ʱ
                    cMenus.add(p);//ֱ����ӽ���
                }else {//��ǰû���Ӳ˵�ʱ,cMenus�ǿյ�
                    cMenus = new ArrayList<>();//��Ҫnewһ��List
                    cMenus.add(p);
                    //��ϸ��Ӳ˵�
                    permission.setChildren(cMenus);
                }
            }
        }
        return pMenus;
    }

    @Override
    public List<Integer> queryPermissionIdsByRoleId(Integer roleid) {
        return pDao.queryPermissionIdsByRoleId(roleid);
    }

    @Override
    public List<Permission> getAll() {
        return pMapper.selectByExample(null);
    }

}
