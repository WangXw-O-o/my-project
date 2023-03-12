package com.wxw.data.dao.mysql;

import com.wxw.data.pojo.mysql.Ingredient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface IngredientMapper {

    List<Ingredient> getAll();

}
