package com.HYX.webDev.service;

import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;


public interface BlogCategoryService {
    PageResult getCategoryPage(PageUtil pageUtil);
    Boolean AddCategory(String CategoryName, String CategoryIcon);
    Boolean ModifyCategory(Integer categoryId,String CategoryName, String CategoryIcon);
    Boolean DeleteCategory(Integer CategoryId);
    Boolean DeleteCategoryBatch(Integer[] ids);
    BlogCategory CategoryInfo(Integer categoryId);
}
