package com.HYX.webDev.service;

import com.HYX.webDev.entity.AdminUser;


public interface AdminUserService {

     AdminUser login(String username, String password);
     AdminUser getUserDetailById(Integer loginUserId);
     Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);
     Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

}
