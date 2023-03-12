package com.wxw.data.dao;

import com.wxw.data.pojo.Ingredient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IngredientMapper {

    List<Ingredient> getAll();

}
