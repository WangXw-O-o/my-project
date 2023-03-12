package com.wxw.data.service.oracle.impl;

import com.wxw.data.dao.oracle.TestMapper;
import com.wxw.data.pojo.oracle.Test;
import com.wxw.data.service.oracle.TestMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestMapperServiceImpl implements TestMapperService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> getAll() {
        return testMapper.getAll();
    }
}
