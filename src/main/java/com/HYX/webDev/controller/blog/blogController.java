package com.HYX.webDev.controller.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class blogController {
    @GetMapping({"/index","/","/index.html"})
    public String index() {
        return "blog/index";
    }
}
