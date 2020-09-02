package com.HYX.webDev.service.Impl;

import com.HYX.webDev.controller.vo.BlogListVO;
import com.HYX.webDev.dao.BlogCategoryMapper;
import com.HYX.webDev.dao.BlogMapper;
import com.HYX.webDev.dao.BlogTagMapper;
import com.HYX.webDev.dao.BlogTagRelationMapper;
import com.HYX.webDev.entity.Blog;
import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.entity.BlogTag;
import com.HYX.webDev.entity.BlogTagRelation;
import com.HYX.webDev.service.BlogService;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Transactional
    public String saveBlog(Blog blog){
        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if(blogCategory == null){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("default category");
        }
        else {
            //get the category name
            String CategoryName = blogCategory.getCategoryName();
            blog.setBlogCategoryName(CategoryName);
        }
        String[] blogtags = blog.getBlogTags().split(",");
        if(blogtags.length > 6)
        {
            return "the number of tags can not be more than 6!";
        }
        //insert blog
        if(blogMapper.insertSelective(blog) > 0){
            //nonexistent new blog tags
            List<BlogTag> newBlogTag = new ArrayList<>();
            //existent blog tags
            List<BlogTag> AllBlogTag = new ArrayList<>();
            for(int i = 0;i < blogtags.length; i++){
                BlogTag blogtag = blogTagMapper.selectByTagName(blogtags[i]);

                if(blogtag == null)
                {
                    BlogTag blogTag = new BlogTag();
                    blogTag.setTagName(blogtags[i]);
                    newBlogTag.add(blogTag);
                }
                else{
                    AllBlogTag.add(blogtag);
                }
            }
            if(!CollectionUtils.isEmpty(newBlogTag)){
                //insert new blog tags
                blogTagMapper.InsertBatch(newBlogTag);
                AllBlogTag.addAll(newBlogTag);
            }
            //create new bind between blog and tags
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            for(BlogTag blogtag : AllBlogTag){
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setTagId(blogtag.getTagId());
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelations.add(blogTagRelation);
            }
            if(blogTagRelationMapper.insertBatch(blogTagRelations)>0) {
                return "success";
            }
        }
        return "fail";
    }
    @Override
    public PageResult getBlogPage(PageUtil pageUtil) {
        //the data of the current page
        List<Blog> blogs =blogMapper.findBlog(pageUtil);
        //the number of all records
        int total = blogMapper.getTotalBlog(pageUtil);
        PageResult pageResult = new PageResult(blogs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public Boolean DeleteBlogBatch(Long[] ids){
        //delete blog and its relation
        if(blogMapper.deleteBatch(ids) > 0 && blogTagRelationMapper.delteByBlogIds(ids)>0){
            return true;
        }
        return false;
    }
    //get blog information and page information from backend
    @Override
    public PageResult getBlogsForIndexPage(int page){
        Map params = new HashMap();
        params.put("page", page);
        //4 record of every page
        params.put("limit", 4);
        params.put("blogStatus", 1);//filter the publish blog
        PageUtil pageUtil = new PageUtil(params);
        List<Blog> blogList = blogMapper.findBlog(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlog(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
                List<BlogCategory> blogCategories = blogCategoryMapper.selectByCategoryIds(categoryIds);
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("default category");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/1.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }
    @Override
    public PageResult getBlogsPageBySearch(int pageNum,String keyword){
        Map params = new HashMap();
        params.put("page",  pageNum);
        params.put("keyword",keyword);
        //4 record of every page
        params.put("limit", 4);
        params.put("blogStatus", 1);//filter the publish blog
        PageUtil pageUtil = new PageUtil(params);
        List<Blog> blogList = blogMapper.findBlog(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlog(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;

    }
}
