package com.HYX.webDev.dao;

import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.entity.Link;

import java.util.List;
import java.util.Map;

public interface LinkMapper {
    int deleteByPrimaryKey(Long linkId);

    int insert(Link record);

    int insertSelective(Link record);

    Link selectByPrimaryKey(Long linkId);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);

    List<Link> findLink(Map param);

    List<Link> findAllLinks();

    int getTotalLink(Map param);

    int deleteBatch(Integer[] ids);

}