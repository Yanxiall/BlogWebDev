package com.HYX.webDev.service.Impl;

import com.HYX.webDev.controller.vo.BlogDetailVo;
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
import com.HYX.webDev.util.MarkDownUtil;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        if(blogtags.length > 20)
        {
            return "the number of tags can not be more than 20!";
        }
        //insert blog
        if(blogMapper.insertSelective(blog) > 0){
            //nonexistent new blog tags
            List<BlogTag> newBlogTag = new ArrayList<>();
            //existent blog tags
            List<BlogTag> AllBlogTag = new ArrayList<>();
            for(int i = 0;i < blogtags.length; i++){
                List<BlogTag> blogtag = blogTagMapper.selectByTagName(blogtags[i]);

                if(blogtag.size() == 0)
                {
                    BlogTag blogTag = new BlogTag();
                    blogTag.setTagName(blogtags[i]);
                    newBlogTag.add(blogTag);
                }
                else{
                    AllBlogTag.add(blogtag.get(0));
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
        params.put("limit", 6);
        params.put("blogStatus", 1);//filter the publish blog
        PageUtil pageUtil = new PageUtil(params);
        List<Blog> blogList = blogMapper.findBlog(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlog(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;

    }
    @Override
    public PageResult getBlogsPageByCategory(int pageNum,String categoryName){
        Map params = new HashMap();
        params.put("page",  pageNum);
        params.put("blogCategoryName",categoryName);
        //4 record of every page
        params.put("limit", 6);
        params.put("blogStatus", 1);//filter the publish blog
        PageUtil pageUtil = new PageUtil(params);
        List<Blog> blogList = blogMapper.findBlog(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlog(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    //search blog by tag
    @Override
    public PageResult getBlogsPageByTag(int pageNum,String tagName){
        List<BlogTag> tag = blogTagMapper.selectByTagName(tagName);
        Map params = new HashMap();
        params.put("page",  pageNum);
        params.put("tagId",tag.get(0).getTagId());
        //4 record of every page
        params.put("limit", 6);
        params.put("blogStatus", 1);//filter the publish blog
        PageUtil pageUtil = new PageUtil(params);
        List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlogbyTagId(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    @Override
    public BlogDetailVo getBlogDetail(Long blogId){
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        BlogDetailVo blogDetailVo = getBlogDetailVO(blog);
        if (blogDetailVo != null) {
            return blogDetailVo;
        }
        return null;

    }
    private BlogDetailVo getBlogDetailVO(Blog blog) {
        //judge ob blog is null or publish
        if (blog != null && blog.getBlogStatus() == 1) {
            //add blogView
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKey(blog);
            BlogDetailVo blogDetailVO = new BlogDetailVo();
            BeanUtils.copyProperties(blog, blogDetailVO);
            //transform blog content
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("default category");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //get category Icon
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (!StringUtils.isEmpty(blog.getBlogTags())) {
                //split tags
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            return blogDetailVO;
        }
        return null;

    }
    @Override
    @Transactional
    public String updateBlog(Blog blog){
        Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if (blogForUpdate == null) {
            return "blog exists not";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("defalult category");
        } else {
            //set category name
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
        }
        //process tags
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 20) {
            return "the number of tags can not be more than 20!";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        //new tags
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //create relation
        List<BlogTag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            List<BlogTag> tag = blogTagMapper.selectByTagName(tags[i]);
            if (tag.size() == 0) {
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag.get(0));
            }
        }
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            blogTagMapper.InsertBatch(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        blogCategoryMapper.updateByPrimaryKeySelective(blogCategory);
        blogTagRelationMapper.delteByBlogId(blog.getBlogId());
        blogTagRelationMapper.insertBatch(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            return "success";
        }
        return "modify fail";

    }
}
