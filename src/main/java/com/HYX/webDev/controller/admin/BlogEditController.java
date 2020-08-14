package com.HYX.webDev.controller.admin;

import com.HYX.webDev.service.BlogCategoryService;
import com.HYX.webDev.util.MyBlogUtils;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping({"/edit"})
    public String category(HttpServletRequest request) {
        request.setAttribute("categories",blogCategoryService.getAllCategories());
        return "admin/edit";
    }
    @PostMapping("/blogs/md/uploadfile")
    public void uploadFileByEditormd(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(name = "editormd-image-file", required = true)
                                             MultipartFile file) throws IOException, URISyntaxException {
        String FILE_UPLOAD_DIC = "/home/hadoop/IdeaProjects/BlogWebDev/upload/";//upload file to the directory
        String fileName= file.getOriginalFilename();
        String name =  fileName.substring(0, fileName.indexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //create file
        File destFile = new File(FILE_UPLOAD_DIC + fileName);
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
        //create file directory
        File fileDirectory = new File(FILE_UPLOAD_DIC);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("create file directory fail:" + fileDirectory);
                }
            }
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
