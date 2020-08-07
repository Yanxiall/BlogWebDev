package com.HYX.webDev.service;

import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.Result;

public interface BlogCategoryService {
    PageResult getCategoryPage(PageUtil pageUtil);
    Boolean AddCategory(String CategoryName, String CategoryIcon);
    Boolean ModifyCategory(Integer categoryId,String CategoryName, String CategoryIcon);
    Boolean DeleteCategory(Integer CategoryId);
    BlogCategory CategoryInfo(Integer categoryId);
}
