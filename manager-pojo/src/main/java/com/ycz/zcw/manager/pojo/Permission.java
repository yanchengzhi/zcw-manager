package com.ycz.zcw.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class Permission {
    
    private Integer id;

    private Integer pid;

    private String name;

    private String icon;

    private String url;
    
    private List<Permission> children = new ArrayList<>();//�����˵�������һ���Ӳ˵�
    
    private boolean open = true;//��Ҷ�ӵĿ��أ�Ĭ��Ϊչ��
    
    private boolean checked = false;//��ѡ��Ĺ�ѡ״̬��Ĭ���ǲ���ѡ��
    
    public List<Permission> getChildren() {
        return children;
    }

    
    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }


    
    public boolean isOpen() {
        return open;
    }


    
    public void setOpen(boolean open) {
        this.open = open;
    }

    
    public boolean isChecked() {
        return checked;
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    
}