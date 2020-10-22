package com.HYX.webDev.dao;

import com.HYX.webDev.entity.BlogTag;
import com.HYX.webDev.entity.BlogTagRelation;

import java.util.List;

public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Long relationId);

    int delteByBlogIds(Long[] ids);

    int delteByBlogId(Long id);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    BlogTagRelation selectByPrimaryKey(Long relationId);

    List<Long> SelectDistinctId(Integer[] ids);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);
//insert a batch
    int insertBatch(List<BlogTagRelation> blogTagRelations);
}