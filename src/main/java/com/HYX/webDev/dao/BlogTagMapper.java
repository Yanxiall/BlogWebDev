package com.HYX.webDev.dao;

import com.HYX.webDev.entity.BlogTag;
import com.HYX.webDev.entity.TagShow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface BlogTagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByTagName(String tagName);

    BlogTag selectByPrimaryKey(Integer tagId);

    int deleteBatch(Integer[] ids);

    List<BlogTag> findBlogTag(Map param);

    int getTotalBlogTag(Map param);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    int InsertBatch(List<BlogTag> BlogTags);

    List<TagShow> getTagShow();
}