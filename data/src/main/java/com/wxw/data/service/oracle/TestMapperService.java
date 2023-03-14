package com.wxw.data.service.oracle;

import com.wxw.data.pojo.oracle.Test;

import java.util.List;

public interface TestMapperService {

    List<Test> getAll();

    Test getOne(Long id);

    Test getOneById(Long id);

    Test getOneByIdAndName(Long id, String name);

}
