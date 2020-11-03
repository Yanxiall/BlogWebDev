package com.HYX.webDev.service.Impl;

import com.HYX.webDev.service.MailService;
import com.HYX.webDev.util.MailUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;

@Service
public class MailServiceImpl  implements MailService {

    @Resource
    private MailUtil mailUtil;

    @Override
    public void sendRegMail(String Name,String Email,String PhoneNumber,String Company,String message) {
        String from = "yanxiahechn@gmail.com";
        String[] to = {"yanxia_he@126.com"};
        String subject = "message from your website";
        HashMap<String,String> content= new HashMap<String,String>();
        content.put("name",Name);
        content.put("Email",Email);
        content.put("PhoneNumber",PhoneNumber);
        content.put("Company",Company);
        content.put("message",message);
        String templateName= "blog/mail.html";
        try {
            mailUtil.sendHtmlMail(from, to, null, null, subject, templateName, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("send email wrong");
        }
    }

}

