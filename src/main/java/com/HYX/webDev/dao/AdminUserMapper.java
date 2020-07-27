package com.HYX.webDev.dao;

import com.HYX.webDev.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer adminUserId);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer adminUserId);
    AdminUser search(@Param("username") String username, @Param("password") String password);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}