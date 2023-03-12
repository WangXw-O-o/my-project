package com.wxw.data.service.impl;

import com.wxw.data.dao.IngredientMapper;
import com.wxw.data.pojo.Ingredient;
import com.wxw.data.service.IngredientMapperService;
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
