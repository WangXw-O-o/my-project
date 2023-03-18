package com.wxw.springbootdemo.data;

import com.wxw.data.pojo.mysql.Ingredient;
import com.wxw.data.service.mysql.IngredientMapperService;
import com.wxw.data.service.oracle.TestMapperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MybatisTest {

    @Autowired
    private IngredientMapperService ingredientMapperService;
    @Autowired
    private TestMapperService testMapperService;

    @Test
    public void testGetAll() {
        List<Ingredient> all = ingredientMapperService.getAll();
        System.out.println(all);
    }

    @Test
    public void setTestMapperService() {
//        List<com.wxw.data.pojo.oracle.Test> all = testMapperService.getAll();
//        System.out.println(all);
        com.wxw.data.pojo.oracle.Test one = testMapperService.getOne(1L);
        System.out.println(one);
//        com.wxw.data.pojo.oracle.Test two = testMapperService.getOneById(2L);
//        System.out.println(two);
//        com.wxw.data.pojo.oracle.Test three = testMapperService.getOneByIdAndName(3L, "杨五1");
//        System.out.println(three);
    }

    @Test
    public void mysqlInsertOneTest() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId("4");
        ingredient.setName("hhhh");
        ingredient.setType("12312");
        int insert = ingredientMapperService.insert(ingredient);
        System.out.println(insert);
    }

    @Test
    public void mysqlInsertBatchTest() {
        List<Ingredient> list = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(i + "");
            ingredient.setName("name"+i);
            ingredient.setType("type" + (i % 5));
            list.add(ingredient);
        }
        ingredientMapperService.insertBatchBySql(list);
//        ingredientMapperService.insertBatchBySqlSession(list);
    }

}
