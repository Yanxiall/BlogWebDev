package com.HYX.webDev.dao;

import com.HYX.webDev.entity.contact;

public interface contactMapper {
    int deleteByPrimaryKey(Long contactId);

    int insert(contact record);

    int insertSelective(contact record);

    contact selectByPrimaryKey(Long contactId);

    int updateByPrimaryKeySelective(contact record);

    int updateByPrimaryKeyWithBLOBs(contact record);

    int updateByPrimaryKey(contact record);
}