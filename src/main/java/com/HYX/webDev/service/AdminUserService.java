package com.HYX.webDev.service;

import com.HYX.webDev.entity.AdminUser;

public interface AdminUserService {

     AdminUser login(String username, String password);
}
