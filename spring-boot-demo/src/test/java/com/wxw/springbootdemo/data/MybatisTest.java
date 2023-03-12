package com.wxw.springbootdemo.data;

import com.wxw.data.pojo.Ingredient;
import com.wxw.data.service.IngredientMapperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisTest {

    @Autowired
    private IngredientMapperService ingredientMapperService;

    @Test
    public void testGetAll() {
        List<Ingredient> all = ingredientMapperService.getAll();
        System.out.println(all);
    }

}
