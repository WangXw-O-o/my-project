package com.wxw.data.dao.oracle;

import com.wxw.data.pojo.oracle.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper {

    List<Test> getAll();

    /**
     * 传入参数的 3 种方式：
     * （1）${}中使用1，2，....表示是第几个参数
     * （2）@Param("id")定义参数名，${id}
     * （3）使用 Map 作为参数传入，key值就是参数名
     * 该方法不能被重载
     * @param id
     * @return
     */
    Test getOne(Long id);

    Test getOneById(Long id);

    Test getOneByIdAndName(@Param("id") Long id, @Param("name") String name);


}
