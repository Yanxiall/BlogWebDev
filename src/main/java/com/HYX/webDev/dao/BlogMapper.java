package com.HYX.webDev.dao;

import com.HYX.webDev.entity.Blog;
import com.HYX.webDev.entity.BlogCategory;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    int deleteByPrimaryKey(Long blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> findBlog(Map param);

    int getTotalBlog(Map param);

    int deleteBatch(Long[] ids);


}