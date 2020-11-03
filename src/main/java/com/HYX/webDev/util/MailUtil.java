package com.HYX.webDev.util;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;


@Component
public class MailUtil {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    TemplateEngine templateEngine;

    //send html content with Thymeleaf rendered
    public void sendHtmlMail(String from, String[] to, String[] cc, String[] bcc, String subject, String templateName, HashMap<String,String> content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(to);
        //make a copy for and can view the other receiver
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        //Blind carbon copy and can not view the other receiver
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());
        //generate template engine context
        Context context = new Context();
        if (content != null && content.size() > 0) {
            for (String key : content.keySet()) {
                context.setVariable(key, content.get(key));
            }
        }
        String process = templateEngine.process(templateName, context);
        helper.setText(process,true);
        javaMailSender.send(mimeMessage);
    }

}