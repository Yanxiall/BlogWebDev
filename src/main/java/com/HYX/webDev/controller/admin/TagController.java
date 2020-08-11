package com.HYX.webDev.controller.admin;

import com.HYX.webDev.service.BlogTagService;
import com.HYX.webDev.util.Constants;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.Result;
import com.HYX.webDev.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Resource
    private BlogTagService blogTagService;
    @GetMapping({"/tags"})
    public String category() {
        return "admin/tags";
    }

    @RequestMapping(value ="/tags/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "wrong parameters!");
        }
        //query the page which is searched
        PageUtil pageUtil = new PageUtil(params);
        //return the frontend data
        return ResultGenerator.genSuccessResult(blogTagService.getTagPage(pageUtil));
    }
    @RequestMapping(value ="/tags/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam String TagName){
        if(StringUtils.isEmpty(TagName))
        {
            return ResultGenerator.genFailResult("Please input tag name!");
        }
        if(blogTagService.AddTag(TagName)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Duplicate Tag!");
        }

    }

    @RequestMapping(value ="/tags/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result modify(@RequestBody Integer[] ids ){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("invalid parameter!");
        }
        if(blogTagService.DeleteTagBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Tag is related to the article!");
        }
    }
}




