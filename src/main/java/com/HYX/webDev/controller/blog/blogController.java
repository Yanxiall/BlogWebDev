package com.HYX.webDev.controller.blog;

import com.HYX.webDev.service.BlogTagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blog")
public class blogController {
    @Resource
    private BlogTagService blogTagService;

    @GetMapping({"/index","/","/index.html"})
    public String index() {
        return "blog/index";
    }

    @GetMapping({"/contact"})
    public String contact() {
        return "blog/contact";
    }

    @GetMapping({"/projects"})
    public String projects(HttpServletRequest request) {
        request.setAttribute("hotTags",blogTagService.BlogTagShow());
        return "blog/projects";
    }

}
