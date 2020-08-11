package com.HYX.webDev.service.Impl;



import com.HYX.webDev.dao.BlogTagMapper;
import com.HYX.webDev.dao.BlogTagRelationMapper;
import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.entity.BlogTag;
import com.HYX.webDev.service.BlogTagService;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogTagImpl implements BlogTagService {
    @Resource
    private BlogTagMapper blogTagMapper;
    @Resource
    private BlogTagRelationMapper blogTagRelationMapper;
    @Override
    public PageResult getTagPage(PageUtil pageUtil) {
        //the data of the current page
        List<BlogTag> categories = blogTagMapper.findBlogTag(pageUtil);
        //the number of all records
        int total = blogTagMapper.getTotalBlogTag(pageUtil);
        PageResult pageResult = new PageResult(categories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    @Override
    public Boolean AddTag (String TagName){
        BlogTag blogTag = new BlogTag();
        blogTag.setTagName(TagName);
        if(blogTagMapper.insertSelective(blogTag) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean DeleteTag(Integer TagId){

        if(blogTagMapper.deleteByPrimaryKey(TagId) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean DeleteTagBatch(Integer[] ids){
        if(blogTagRelationMapper.SelectDistinctId(ids).size() >= 1){
            return false;
        }
        return blogTagMapper.deleteBatch(ids) > 0;
    }
}
