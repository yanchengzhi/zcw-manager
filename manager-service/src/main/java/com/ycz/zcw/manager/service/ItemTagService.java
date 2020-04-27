package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.Tag;

public interface ItemTagService {

    List<Tag> getAll();//获取所有节点

    Tag queryTagById(Integer id);//按ID查询标签

    Tag queryTagByName(String name);//按名称查找标签

    void addTag(Tag tag);//添加标签

    List<Tag> queryTwoTags();//获取所有一级菜单

    void editTag(Tag tag);//修改标签

    void deleteTag(Integer id);//删除标签

}
