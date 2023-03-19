package com.wxw.data.service.mysql;

import com.wxw.data.pojo.mysql.Ingredient;

import java.util.List;

public interface IngredientMapperService {

    List<Ingredient> getAll();

    int insert(Ingredient ingredient);

    /**
     * mysql 对 sql 语句的长度有限制（默认是4M），不能直接将 list 全部组装成一个 sql 进行插入
     * 1、需要使用事务
     * 2、预估长度
     *
     * 10000条耗时960ms+，单次拼装的更多还会执行的更快
     * @param list
     * @return
     */
    int insertBatchBySql(List<Ingredient> list);

    /**
     * 手动创建 SqlSession 来执行，还是每次只插入一条数据
     * 避免了每次都去创建 SqlSession 的开销
     *
     * 10000条耗时15000ms+
     * @param list
     * @return
     */
    int insertBatchBySqlSession(List<Ingredient> list);
}
