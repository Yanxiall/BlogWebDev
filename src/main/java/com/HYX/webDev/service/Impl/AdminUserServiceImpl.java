package com.HYX.webDev.service.Impl;

import com.HYX.webDev.dao.AdminUserMapper;
import com.HYX.webDev.entity.AdminUser;
import com.HYX.webDev.service.AdminUserService;
import com.HYX.webDev.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {
      @Resource
      private AdminUserMapper adminUserMapper;
      @Override
      public AdminUser login(String username,String password){
            String EncryPassword = MD5Util.MD5Encode(password, "UTF-8");
            return adminUserMapper.search(username,EncryPassword);
      }
}
