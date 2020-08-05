package com.HYX.webDev.controller.admin;

import com.HYX.webDev.controller.common.Constants;
import com.HYX.webDev.controller.common.Result;
import com.HYX.webDev.controller.common.ResultGenerator;
import com.HYX.webDev.service.AdminUserService;
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
        return ResultGenerator.genSuccessResult(blogCategoryService.getCategoryPage(pageUtil));
    }
}
