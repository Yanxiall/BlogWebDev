package com.HYX.webDev.controller.admin;

import com.HYX.webDev.util.MyBlogUtils;
import com.HYX.webDev.util.Result;
import com.HYX.webDev.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/admin")
@ResponseBody
public class UploadController {
    @PostMapping("/upload")
    public Result upload(HttpServletRequest request,
                         @RequestParam("file") MultipartFile file)throws IOException, URISyntaxException {
        System.out.println("come");
        String FILE_UPLOAD_DIC = "/home/hadoop/IdeaProjects/BlogWebDev/upload/";//upload file to the directory
        String fileName = file.getOriginalFilename();
        String name = fileName.substring(0, fileName.indexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //create file
        File destFile = new File(FILE_UPLOAD_DIC + fileName);
        int i = 1;
        while (destFile.exists()) {
            StringBuilder tempName = new StringBuilder();
            tempName.append(name).append(i).append(suffix);
            fileName = tempName.toString();
            String parentPath = destFile.getParent();
            destFile = new File(parentPath + File.separator + fileName);
            i++;
        }
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + fileName.substring(0, fileName.indexOf(".")) + suffix;
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
        Result result = new Result();
        result = ResultGenerator.genSuccessResult();
        result.setData(fileUrl);
        return result;
    }

}
