package com.wxw.data.service.mysql.impl;

import com.wxw.data.dao.mysql.IngredientMapper;
import com.wxw.data.pojo.mysql.Ingredient;
import com.wxw.data.service.mysql.IngredientMapperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IngredientMapperServiceImpl implements IngredientMapperService {

    @Resource
    private IngredientMapper ingredientMapper;
    @Resource(name = "mysqlSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Ingredient> getAll() {
        return ingredientMapper.getAll();
    }

    @Override
    public int insert(Ingredient ingredient) {
        return ingredientMapper.insertOne(ingredient);
    }

    @Override
    public int insertBatchBySql(List<Ingredient> list) {
        int successCount = 0;
        try {
            int count = 0;
            List<Ingredient> executeList = null;
            log.info("[insert]开始，共插入[{}]条。", list.size());
            long start = System.currentTimeMillis();
            for (int i = 0; i < list.size(); i++) {
                if (count == 0) {
                    executeList = new ArrayList<>();
                }
                executeList.add(list.get(i));
                count++;
                if (count == 1000 || list.size() == i+1) {
                    successCount = insertBatchTransactional(executeList) + successCount;
                    count = 0;
                }
            }
            long end = System.currentTimeMillis();
            log.info("[insert]结束，成功插入[{}]条，耗时[{}ms]", successCount, (end - start));
        } catch (Exception e) {
            log.info("事务执行异常！", e);
        }
        return successCount;
    }

    @Transactional
    public int insertBatchTransactional(List<Ingredient> list) {
        if (list == null) {
            return 0;
        }
        log.info("[insert]事务开始，共插入[{}]条。", list.size());
        long start = System.currentTimeMillis();
        int result = ingredientMapper.insertBatch(list);
        long end = System.currentTimeMillis();
        log.info("[insert]事务结束，成功插入[{}]条，耗时[{}ms]", result, (end - start));
        return result;
    }

    @Override
    public int insertBatchBySqlSession(List<Ingredient> list) {
        int count = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        IngredientMapper mapper = sqlSession.getMapper(IngredientMapper.class);
        log.info("[insert]开始，共插入[{}]条。", list.size());
        long start = System.currentTimeMillis();
        try {
            for (Ingredient ingredient : list) {
                mapper.insertOne(ingredient);
            }
            sqlSession.commit();
            count = list.size();
        } catch (Exception e) {
            log.error("插入执行异常, 回滚。", e);
            sqlSession.rollback();
        }
        long end = System.currentTimeMillis();
        log.info("[insert]结束，成功插入[{}]条，耗时[{}ms]", count, (end - start));
        sqlSession.clearCache();
        return count;
    }
}
