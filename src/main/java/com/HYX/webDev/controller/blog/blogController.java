package com.HYX.webDev.controller.blog;

import com.HYX.webDev.entity.contact;
import com.HYX.webDev.service.BlogService;
import com.HYX.webDev.service.BlogTagService;
import com.HYX.webDev.service.ContactService;
import com.HYX.webDev.util.Constants;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.Result;
import com.HYX.webDev.util.ResultGenerator;
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
    @GetMapping({"/index","/","/index.html"})
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
    public Result submitContact(@RequestParam(name="contactName") String Name,
                                @RequestParam(name="contactEmail") String Email,
                                @RequestParam(name="contactPhoneNumber") String PhoneNumber,
                                @RequestParam(name="contactCompany") String Company,
                                @RequestParam(name="contactMessage") String message
                                ){
        contact contact = new contact();
        contact.setContactName(Name);
        contact.setContactEmail(Email);
        contact.setContactPhoneNumber(PhoneNumber);
        contact.setContactCompany(Company);
        contact.setContactMessage(message);

        String saveResult = contactService.saveContact(contact);
        if("success".equals(saveResult)){
            return ResultGenerator.genSuccessResult("save success!");
        }
        else{
            return ResultGenerator.genFailResult(saveResult);
        }
    }
    //project page
    @GetMapping({"/projects"})
    public String projects(HttpServletRequest request) {
        request.setAttribute("hotTags",blogTagService.BlogTagShow());
        return "blog/projects";
    }
    //set the projects pages
    @RequestMapping(value ="/blog/projects/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "wrong parameters!");
        }
        //query the page which is searched
        PageUtil pageUtil = new PageUtil(params);
        //return the frontend data
        return ResultGenerator.genSuccessResult(blogService.getBlogPage(pageUtil));
    }

}
