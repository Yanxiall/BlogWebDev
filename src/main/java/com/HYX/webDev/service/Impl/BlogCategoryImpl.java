package com.HYX.webDev.service.Impl;

import com.HYX.webDev.dao.BlogCategoryMapper;
import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.service.BlogCategoryService;
import com.HYX.webDev.util.Constants;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.Result;
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
    @Override
    public Boolean AddCategory (String CategoryName, String CategoryIcon){
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setCategoryName(CategoryName);
        blogCategory.setCategoryIcon(CategoryIcon);
        if(blogCategoryMapper.insertSelective(blogCategory) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean ModifyCategory(Integer categoryId,String CategoryName, String CategoryIcon){

        BlogCategory blogCategory =  blogCategoryMapper.selectByPrimaryKey(categoryId);
        blogCategory.setCategoryName(CategoryName);
        blogCategory.setCategoryIcon(CategoryIcon);
        if(blogCategoryMapper.updateByPrimaryKeySelective(blogCategory) > 0){
            return true;
        }
        return false;
    }
    @Override
    public BlogCategory CategoryInfo(Integer categoryId){
        BlogCategory blogCategory =  blogCategoryMapper.selectByPrimaryKey(categoryId);

        return blogCategory;
    }
    @Override
    public Boolean DeleteCategory(Integer CategoryId){

        if(blogCategoryMapper.deleteByPrimaryKey(CategoryId) > 0){
            return true;
        }
        return false;
    }
    @Override
    public Boolean DeleteCategoryBatch(Integer[] ids){
        if(blogCategoryMapper.deleteBatch(ids) > 0){
            return true;
        }
        return false;
    }
}
