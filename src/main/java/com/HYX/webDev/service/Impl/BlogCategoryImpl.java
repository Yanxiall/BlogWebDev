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
        //当前页码中的数据列表
        List<BlogCategory> categories = blogCategoryMapper.findCategory(pageUtil);
        //数据总条数 用于计算分页数据
        int total = blogCategoryMapper.getTotalCategory(pageUtil);
        PageResult pageResult = new PageResult(categories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
