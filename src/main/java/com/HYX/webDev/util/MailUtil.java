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

    //发送html内容的邮件,使用thymeleaf渲染页面
    public void sendHtmlMail(String from, String[] to, String[] cc, String[] bcc, String subject, String templateName, HashMap<String,String> content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(to);
        //抄送,收到邮件用户可以看到其他收件人
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        //密送 收到邮件用户看不到其他收件人
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());
        //生成邮件模板上的内容
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