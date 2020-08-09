package com.HYX.webDev.controller.admin;

import com.HYX.webDev.util.Constants;
import com.HYX.webDev.util.Result;
import com.HYX.webDev.util.ResultGenerator;
import com.HYX.webDev.service.BlogCategoryService;
import com.HYX.webDev.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    private BlogCategoryService blogCategoryService;

    @GetMapping({"/category"})
    public String category() {
        return "admin/category";
    }

    @RequestMapping(value ="/category/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "wrong parameters!");
        }
        //query the page which is searched
        PageUtil pageUtil = new PageUtil(params);
        //return the frontend data
        return ResultGenerator.genSuccessResult(blogCategoryService.getCategoryPage(pageUtil));
    }
    @RequestMapping(value ="/category/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam String CategoryName,@RequestParam String CategoryIcon ){
        if(StringUtils.isEmpty(CategoryName))
        {
            return ResultGenerator.genFailResult("Please input category name!");
        }
        if( StringUtils.isEmpty(CategoryIcon)){
            return ResultGenerator.genFailResult("Please select a category icon!");
        }
        if(blogCategoryService.AddCategory(CategoryName,CategoryIcon)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Duplicate Category!");
        }

    }
    @RequestMapping(value ="/category/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modify(@RequestParam Integer CategoryId, @RequestParam String CategoryName,@RequestParam String CategoryIcon ){
        if (CategoryId == null || CategoryId < 1) {
            return ResultGenerator.genFailResult("invalid parameter!");
        }
        if(StringUtils.isEmpty(CategoryName))
        {
            return ResultGenerator.genFailResult("Please input category name!");
        }
        if( StringUtils.isEmpty(CategoryIcon)){
            return ResultGenerator.genFailResult("please select a category icon!");
        }
        if(blogCategoryService.ModifyCategory(CategoryId,CategoryName,CategoryIcon)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Duplicate Category!");
        }
    }
    @RequestMapping(value ="/category/info", method = RequestMethod.GET)
    @ResponseBody
    public Result info(@RequestParam Integer categoryId){

        return ResultGenerator.genSuccessResult(blogCategoryService.CategoryInfo(categoryId));
    }
    @RequestMapping(value ="/category/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("invalid parameter!");
        }
        if(blogCategoryService.DeleteCategoryBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Delete Fail!");
        }
    }
}



