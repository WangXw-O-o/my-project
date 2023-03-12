package com.wxw.data.service.mysql.impl;

import com.wxw.data.dao.mysql.IngredientMapper;
import com.wxw.data.pojo.mysql.Ingredient;
import com.wxw.data.service.mysql.IngredientMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientMapperServiceImpl implements IngredientMapperService {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public List<Ingredient> getAll() {
        return ingredientMapper.getAll();
    }

}
