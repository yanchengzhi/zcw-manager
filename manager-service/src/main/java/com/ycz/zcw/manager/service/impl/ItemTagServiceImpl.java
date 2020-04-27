package com.ycz.zcw.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.TagMapper;
import com.ycz.zcw.manager.pojo.Tag;
import com.ycz.zcw.manager.service.ItemTagService;

@Service
public class ItemTagServiceImpl implements ItemTagService {
    
    @Autowired
    private TagMapper tMapper;

    @Override
    public List<Tag> getAll() {
        return tMapper.selectByExample(null);
    }

    @Override
    public Tag queryTagById(Integer id) {
        return tMapper.queryTagById(id);
    }

    @Override
    public Tag queryTagByName(String name) {
        return tMapper.queryTagByName(name);
    }

    @Override
    public void addTag(Tag tag) {
       tMapper.insertSelective(tag); 
    }

    @Override
    public List<Tag> queryTwoTags() {
        return tMapper.queryTwoTags();
    }

    @Override
    public void editTag(Tag tag) {
        tMapper.updateByPrimaryKeySelective(tag);
    }

    @Override
    public void deleteTag(Integer id) {
        tMapper.deleteByPrimaryKey(id);
    }

}
