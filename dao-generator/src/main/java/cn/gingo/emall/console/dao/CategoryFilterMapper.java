package cn.gingo.emall.console.dao;

import cn.gingo.emall.console.entity.CategoryFilter;
import cn.gingo.emall.console.entity.CategoryFilterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryFilterMapper {
    int countByExample(CategoryFilterExample example);

    int deleteByExample(CategoryFilterExample example);

    int deleteByPrimaryKey(String id);

    int insert(CategoryFilter record);

    int insertSelective(CategoryFilter record);

    List<CategoryFilter> selectByExampleWithBLOBs(CategoryFilterExample example);

    List<CategoryFilter> selectByExample(CategoryFilterExample example);

    CategoryFilter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CategoryFilter record, @Param("example") CategoryFilterExample example);

    int updateByExampleWithBLOBs(@Param("record") CategoryFilter record, @Param("example") CategoryFilterExample example);

    int updateByExample(@Param("record") CategoryFilter record, @Param("example") CategoryFilterExample example);

    int updateByPrimaryKeySelective(CategoryFilter record);

    int updateByPrimaryKeyWithBLOBs(CategoryFilter record);

    int updateByPrimaryKey(CategoryFilter record);
}