package com.HYX.webDev.service.Impl;

import com.HYX.webDev.dao.BlogCategoryMapper;
import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.service.BlogCategoryService;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogCategoryImpl implements BlogCategoryService {
    @Resource
    private BlogCategoryMapper blogCategoryMapper;
    @Override
    public PageResult getCategoryPage(PageUtil pageUtil) {
        //the data of the current page
        List<BlogCategory> categories = blogCategoryMapper.findCategory(pageUtil);
        //the number of all records
        int total = blogCategoryMapper.getTotalCategory(pageUtil);
        PageResult pageResult = new PageResult(categories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
