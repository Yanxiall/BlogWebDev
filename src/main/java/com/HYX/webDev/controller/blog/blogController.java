package com.HYX.webDev.controller.blog;

import com.HYX.webDev.entity.contact;
import com.HYX.webDev.service.BlogService;
import com.HYX.webDev.service.BlogTagService;
import com.HYX.webDev.service.ContactService;
import com.HYX.webDev.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.HYX.webDev.util.ResultGenerator.genSuccessResult;

@Controller
@RequestMapping("/blog")
public class blogController {
    @Resource
    private BlogTagService blogTagService;
    @Resource
    private ContactService contactService;
    @Resource
    private BlogService blogService;

    //homepage
    @GetMapping({"/index", "/", "/index.html"})
    public String index() {
        return "blog/index";
    }

    @GetMapping({"/contact"})
    public String contact() {
        return "blog/contact";
    }

    //save the contact information
    @PostMapping("/save")
    @ResponseBody
    public Result submitContact(@RequestParam(name = "contactName") String Name,
                                @RequestParam(name = "contactEmail") String Email,
                                @RequestParam(name = "contactPhoneNumber") String PhoneNumber,
                                @RequestParam(name = "contactCompany") String Company,
                                @RequestParam(name = "contactMessage") String message
    ) {
        contact contact = new contact();
        contact.setContactName(Name);
        contact.setContactEmail(Email);
        contact.setContactPhoneNumber(PhoneNumber);
        contact.setContactCompany(Company);
        contact.setContactMessage(message);

        String saveResult = contactService.saveContact(contact);
        if ("success".equals(saveResult)) {
            return ResultGenerator.genSuccessResult("save success!");
        } else {
            return ResultGenerator.genFailResult(saveResult);
        }
    }

    //project webpage
    @GetMapping({"/projects"})
    public String projects(HttpServletRequest request) {
         return this.page(request, 1);
    }
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error";
        }
        request.setAttribute("hotTags", blogTagService.BlogTagShow());
        request.setAttribute("blogPageResult", blogPageResult);

        return "blog/projects";
    }

    //search webpage
    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request,@PathVariable("keyword") String keyword) {
        return this.search(request, 1,keyword);
    }

    @GetMapping({"/search/{pageNum}/{keyword}"})
    public String search(HttpServletRequest request,@PathVariable("pageNum") int pageNum,@PathVariable("keyword") String keyword) {
        PageResult blogPageResult = blogService.getBlogsPageBySearch(pageNum,keyword);
        if(blogPageResult.list.isEmpty()){
            request.setAttribute("pageName","Search");
            request.setAttribute("keyword",keyword);
            request.setAttribute("message","Nothing is found here!");
            return "blog/search";
        }
        if (blogPageResult== null) {
            return "blog/search";
        }
        request.setAttribute("pageName","Search");
        request.setAttribute("keyword",keyword);
        request.setAttribute("pageurl", "search");
        request.setAttribute("blogPageResult", blogPageResult);
        return "blog/search";
    }
    //category search result
    @GetMapping({"/category/{blogCategoryName}"})
    public String searchCategory(HttpServletRequest request,@PathVariable("blogCategoryName") String blogCategoryName) {
        return this.searchCategory(request, 1,blogCategoryName);
    }

    @GetMapping({"/category/{pageNum}/{keyword}"})
    public String searchCategory(HttpServletRequest request,@PathVariable("pageNum") int pageNum,@PathVariable("keyword") String keyword) {
        PageResult blogPageResult = blogService.getBlogsPageByCategory(pageNum,keyword);
        if (blogPageResult== null) {
            return "error";
        }
        request.setAttribute("pageName","Category");
        request.setAttribute("keyword",keyword);
        request.setAttribute("pageurl", "category");
        request.setAttribute("blogPageResult", blogPageResult);
        return "blog/search";
    }

    //tag search result
    @GetMapping({"/tag/{tagName}"})
    public String searchTag(HttpServletRequest request,@PathVariable("tagName") String tagName) {
        return this.searchTag(request, 1,tagName);
    }

    @GetMapping({"/tag/{pageNum}/{keyword}"})
    public String searchTag(HttpServletRequest request,@PathVariable("pageNum") int pageNum,@PathVariable("keyword") String keyword) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(pageNum,keyword);

        if (blogPageResult== null) {
            System.out.println(blogPageResult);
            request.setAttribute("error","Nothing is found");
            return "blog/search";
        }
        request.setAttribute("pageName","Tag");
        request.setAttribute("keyword",keyword);
        request.setAttribute("pageurl", "tag");
        request.setAttribute("blogPageResult", blogPageResult);
        return "blog/search";
    }
}
