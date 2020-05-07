package com.ycz.zcw.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.zcw.manager.dao.BusinessTypeMapper;
import com.ycz.zcw.manager.pojo.BusinessType;
import com.ycz.zcw.manager.service.BusinessTypeService;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {
    
    @Autowired
    private BusinessTypeMapper bMapper;

    @Override
    public List<BusinessType> getAllBusinessTypes() {
        return bMapper.selectByExample(null);
    }

}
