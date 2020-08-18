package com.HYX.webDev.controller.admin;

import com.HYX.webDev.dao.BlogMapper;
import com.HYX.webDev.entity.Blog;
import com.HYX.webDev.service.BlogCategoryService;
import com.HYX.webDev.service.BlogService;
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

@Controller
@RequestMapping("/admin")
public class BlogManage {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private BlogCategoryService blogCategoryService;
    @GetMapping({"/blog"})
    public String category() {
        return "admin/blog";
    }

    @RequestMapping(value ="/blog/list", method = RequestMethod.GET)
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
    @RequestMapping(value ="/blog/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("invalid parameter!");
        }
        if(blogService.DeleteBlogBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Delete Fail!");
        }
    }
    @RequestMapping(value ="/blog/edit/{blogId}", method = RequestMethod.GET)
    public String modify(HttpServletRequest request,@PathVariable("blogId") Long blogId){
        Blog blog = new Blog();
        blog = blogMapper.selectByPrimaryKey(blogId);
        request.setAttribute("blog",blog);
        request.setAttribute("categories",blogCategoryService.getAllCategories());
        return "admin/edit";
    }
}
