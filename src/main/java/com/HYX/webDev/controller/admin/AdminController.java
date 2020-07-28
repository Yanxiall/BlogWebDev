package com.HYX.webDev.controller.admin;

import com.HYX.webDev.entity.AdminUser;
import com.HYX.webDev.service.AdminUserService;
import com.HYX.webDev.service.Impl.AdminUserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")

public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("VerifyCode") String VerifyCode,
                        HttpSession session) {
        //verify the VerifyCode
        String captcha = session.getAttribute("verifyCode") + "";
        if(!VerifyCode.equals(captcha)){
            session.setAttribute("errorMsg", "VerifyCode is wrong");
            return "admin/login";
        }
        //search the adminUser to see if it exists
        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            // session will be open for 2 hours
            session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "userName or password is wrong");
            return "admin/login";
        }


    }
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index() {
        return "admin/index";
    }


}


