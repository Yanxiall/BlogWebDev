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
      @Override
      public AdminUser getUserDetailById(Integer loginUserId) {
            return adminUserMapper.selectByPrimaryKey(loginUserId);
      }
      @Override
      public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
            AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
            if (adminUser != null) {
                  String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
                  String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
                  //change password only when the original password is correct
                  if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
                        //set new password
                        adminUser.setLoginPassword(newPasswordMd5);
                        if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                              //return true when succeed
                              return true;
                        }
                  }
            }
            return false;
      }

      @Override
      public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
            AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
            if (adminUser != null) {
                  //set new login UserName and nickName
                  adminUser.setLoginUserName(loginUserName);
                  adminUser.setNickName(nickName);
                  if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                        //return true when succeed
                        return true;
                  }
            }
            return false;
      }

}
