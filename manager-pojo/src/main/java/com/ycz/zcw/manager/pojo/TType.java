package com.ycz.zcw.manager.pojo;

public class TType {
    private Integer id;

    private String name;
    
    private String introduction;
    
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

    
    public String getIntroduction() {
        return introduction;
    }

    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    
    public String getCreatetime() {
        return createtime;
    }

    
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    
}