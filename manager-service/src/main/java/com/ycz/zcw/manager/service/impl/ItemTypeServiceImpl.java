package com.ycz.zcw.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.TTypeMapper;
import com.ycz.zcw.manager.pojo.TType;
import com.ycz.zcw.manager.service.ItemTypeService;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {
    
    @Autowired
    private TTypeMapper tMapper;

    @Override
    public List<TType> queryTypesPaged(Map<String, Object> map) {
        return tMapper.queryTypesPaged(map);
    }

    @Override
    public int getTypesTotal(Map<String, Object> map) {
        return tMapper.getTypesTotal(map);
    }

    @Override
    public TType queryTypeByName(String name) {
        return tMapper.queryTypeByName(name);
    }

    @Override
    public void addType(TType type) {
        tMapper.insertSelective(type);
    }

    @Override
    public TType queryTypeById(Integer id) {
        return tMapper.queryTypeById(id);
    }

    @Override
    public void editType(TType type) {
        tMapper.updateByPrimaryKeySelective(type);
    }

    @Override
    public void delete(Integer id) {
        tMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteTypes(String typeIds) {
        tMapper.deleteTypes(typeIds);
    }
}
