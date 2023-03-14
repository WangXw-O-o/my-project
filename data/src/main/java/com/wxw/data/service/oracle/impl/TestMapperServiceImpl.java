package com.wxw.data.service.oracle.impl;

import com.wxw.data.dao.oracle.TestMapper;
import com.wxw.data.pojo.oracle.Test;
import com.wxw.data.service.oracle.TestMapperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestMapperServiceImpl implements TestMapperService {

    @Resource
    private TestMapper testMapper;

    @Override
    public List<Test> getAll() {
        return testMapper.getAll();
    }

    @Override
    public Test getOne(Long id) {
        return testMapper.getOne(id);
    }

    @Override
    public Test getOneById(Long id) {
        return testMapper.getOneById(id);
    }

    @Override
    public Test getOneByIdAndName(Long id, String name) {
        return testMapper.getOneByIdAndName(id, name);
    }
}
