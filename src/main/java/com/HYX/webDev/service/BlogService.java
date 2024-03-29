package com.HYX.webDev.service;

import com.HYX.webDev.controller.vo.BlogDetailVo;
import com.HYX.webDev.entity.Blog;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;

public interface BlogService {
    public String saveBlog(Blog blog);
    public String updateBlog(Blog blog);
    PageResult getBlogPage(PageUtil pageUtil);
    public Boolean DeleteBlogBatch(Long[] ids);
    PageResult getBlogsForIndexPage(int page);
    PageResult getBlogsPageBySearch(int pageNum,String keyword);
    PageResult getBlogsPageByCategory(int pageNum,String categoryName);
    PageResult getBlogsPageByTag(int pageNum,String tagName);
    BlogDetailVo getBlogDetail(Long blogId);
}
