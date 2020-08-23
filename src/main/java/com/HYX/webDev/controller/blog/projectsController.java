package com.HYX.webDev.controller.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/blog")
public class projectsController {

        @GetMapping({"/projects"})
        public String projects() {
            return "blog/projects";
        }

}
