package com.wxw.data.dao.mysql;

import com.wxw.data.pojo.mysql.Ingredient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IngredientMapper {

    List<Ingredient> getAll();

    int insertOne(@Param("ingredient") Ingredient ingredient);

    int insertBatch(@Param("list") List<Ingredient> list);
}
