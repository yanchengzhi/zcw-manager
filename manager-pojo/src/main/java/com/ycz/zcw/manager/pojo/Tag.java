package com.ycz.zcw.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Integer id;

    private Integer pid;

    private String name;
    
    private String icon;
    
    private List<Tag> children = new ArrayList<>();//子菜单
    
    private boolean open = true;//子节点是否打开

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
        this.icon = icon;
    }

    
    public List<Tag> getChildren() {
        return children;
    }

    
    public void setChildren(List<Tag> children) {
        this.children = children;
    }

    
    public boolean isOpen() {
        return open;
    }

    
    public void setOpen(boolean open) {
        this.open = open;
    }
    
}