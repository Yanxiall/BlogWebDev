package com.HYX.webDev.dao;

import com.HYX.webDev.entity.Blog;
import com.HYX.webDev.entity.BlogCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface BlogCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(BlogCategory record);

    int insertSelective(BlogCategory record);

    BlogCategory selectByPrimaryKey(Integer categoryId);

    List<BlogCategory> findCategory(Map param);

    List<BlogCategory> findAllCategory();

    int getTotalCategory(Map param);

    int updateByPrimaryKeySelective(BlogCategory record);

    int deleteBatch(Integer[] ids);

    int updateByPrimaryKey(BlogCategory record);
}