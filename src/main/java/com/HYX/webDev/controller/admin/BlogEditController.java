package com.HYX.webDev.controller.admin;

import com.HYX.webDev.entity.Blog;
import com.HYX.webDev.service.BlogCategoryService;
import com.HYX.webDev.service.BlogService;
import com.HYX.webDev.util.MyBlogUtils;
import com.HYX.webDev.util.Result;
import com.HYX.webDev.util.ResultGenerator;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.ResourceUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class BlogEditController {
    @Resource
    private BlogCategoryService blogCategoryService;
    @Resource
    private BlogService blogService;
    @GetMapping({"/edit"})
    public String category(HttpServletRequest request) {
        request.setAttribute("categories",blogCategoryService.getAllCategories());
        return "admin/edit";
    }
 // save the posts
    @PostMapping("/edit/save")
    @ResponseBody
    public Result save(@RequestParam(name="BlogTitle") String BlogTitle,
                       @RequestParam(name="BlogTag")  String BlogTag ,
                       @RequestParam(name="BlogSuburl",required = false)  String BlogSuburl,
                       @RequestParam(name="blogCategoryId") Integer blogCategoryId,
                       @RequestParam(name="blogContent") String blogContent,
                       @RequestParam(name="RandomCoverImg") String CoverImg,
                       @RequestParam(name="PostStatus") Byte PostStatus)
    {
        System.out.println(BlogSuburl);
        if(StringUtils.isEmpty(BlogTitle))
        {
            return ResultGenerator.genFailResult("Please input Blog Title!");
        }
        if (BlogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("Blog Title is too long");
        }
        if(StringUtils.isEmpty(BlogTag))
        {
            return ResultGenerator.genFailResult("Please input Blog Tag!");
        }
        if (BlogTag.trim().length() > 150) {
            return ResultGenerator.genFailResult("Blog Tag is too long");
        }
        if(StringUtils.isEmpty(blogContent))
        {
            return ResultGenerator.genFailResult("Please input blog content!");
        }
        if(StringUtils.isEmpty(CoverImg))
        {
            return ResultGenerator.genFailResult("Please upload cover image!");
        }
        Blog blog = new Blog();
        blog.setBlogTitle(BlogTitle);
        blog.setBlogTags(BlogTag);
        blog.setBlogSubUrl(BlogSuburl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(CoverImg);
        blog.setBlogStatus(PostStatus);
        String saveResult = blogService.saveBlog(blog);
        if("success".equals(saveResult)){
            return ResultGenerator.genSuccessResult("save success!");
        }
        else{
            return ResultGenerator.genFailResult(saveResult);
        }
    }
    @PostMapping("/blogs/update")
    @ResponseBody
    public Result update(
                       @RequestParam(name="blogId") Long blogId,
                       @RequestParam(name="BlogTitle") String BlogTitle,
                       @RequestParam(name="BlogTag")  String BlogTag ,
                       @RequestParam(name="BlogSuburl",required = false)  String BlogSuburl,
                       @RequestParam(name="blogCategoryId") Integer blogCategoryId,
                       @RequestParam(name="blogContent") String blogContent,
                       @RequestParam(name="RandomCoverImg") String CoverImg,
                       @RequestParam(name="PostStatus") Byte PostStatus)
    {
        if(StringUtils.isEmpty(BlogTitle))
        {
            return ResultGenerator.genFailResult("Please input Blog Title!");
        }
        if (BlogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("Blog Title is too long");
        }
        if(StringUtils.isEmpty(BlogTag))
        {
            return ResultGenerator.genFailResult("Please input Blog Tag!");
        }
        if (BlogTag.trim().length() > 150) {
            return ResultGenerator.genFailResult("Blog Tag is too long");
        }
        if(StringUtils.isEmpty(blogContent))
        {
            return ResultGenerator.genFailResult("Please input blog content!");
        }
        if(StringUtils.isEmpty(CoverImg))
        {
            return ResultGenerator.genFailResult("Please upload cover image!");
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(BlogTitle);
        blog.setBlogTags(BlogTag);
        blog.setBlogSubUrl(BlogSuburl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(CoverImg);
        blog.setBlogStatus(PostStatus);
        String updateBlogResult = blogService.updateBlog(blog);
        if ("success".equals(updateBlogResult)) {
            return ResultGenerator.genSuccessResult("modify success!");
        } else {
            return ResultGenerator.genFailResult(updateBlogResult);
        }
    }
    //upload the images
    @PostMapping("/blogs/md/uploadfile")
    public void uploadFileByEditormd(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(name = "editormd-image-file", required = true)
                                             MultipartFile file) throws IOException, URISyntaxException {

        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(),"upload/");
        if(!upload.exists()) {
            upload.mkdirs();
        }

        String fileName= file.getOriginalFilename();
        String name =  fileName.substring(0, fileName.indexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //create file
        File destFile = new File(upload,fileName);
        int i = 1;
        while(destFile.exists()) {
            StringBuilder tempName = new StringBuilder();
            tempName.append(name).append(i).append(suffix);
            fileName = tempName.toString();
            String parentPath = destFile.getParent();
            destFile = new File(parentPath+ File.separator+ fileName);
            i++;
        }
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + fileName.substring(0, fileName.indexOf("."))+ suffix;
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");
        } catch (UnsupportedEncodingException e) {
            response.getWriter().write("{\"success\":0}");
        } catch (IOException e) {
            response.getWriter().write("{\"success\":0}");
        }
    }
}
