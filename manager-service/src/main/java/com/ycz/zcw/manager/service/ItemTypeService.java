package com.ycz.zcw.manager.service;

import java.util.List;
import java.util.Map;

import com.ycz.zcw.manager.pojo.TType;

public interface ItemTypeService {

    List<TType> queryTypesPaged(Map<String, Object> map);//分页查询

    int getTypesTotal(Map<String, Object> map);//获取总记录条数

    TType queryTypeByName(String name);//按名称查询分类

    void addType(TType type);//添加分类

    TType queryTypeById(Integer id);//按照ID查询分类

    void editType(TType type);//修改分类

    void delete(Integer id);//删除分类

    void deleteTypes(String typeIds);//批量删除分类

}
