package com.ycz.zcw.manager.pojo;

public class Role {
    private Integer id;

    private String name;
    
    private String createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getCreatetime() {
        return createtime;
    }

    
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", createtime=" + createtime + "]";
    }
    
    
}