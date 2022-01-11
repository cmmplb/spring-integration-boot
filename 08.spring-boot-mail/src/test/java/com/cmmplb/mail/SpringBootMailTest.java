package com.cmmplb.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class SpringBootMailTest {

    @Resource
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 简单邮件
     */
    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1045599379@qq.com");
        message.setTo("1045599379@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }

    /**
     * 发送附件
     */
    @Test
    public void sendAttachmentsMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1045599379@qq.com");
        helper.setTo("1045599379@qq.com");
        helper.setSubject("主题：有附件");
        helper.setText("有附件的邮件");

        FileSystemResource file = new FileSystemResource(new File("src/main/resources/笑死.jpg"));
        helper.addAttachment("附件-1.jpg", file);
        helper.addAttachment("附件-2.jpg", file);

        mailSender.send(mimeMessage);
    }

    /**
     * 嵌入静态资源
     */
    @Test
    public void sendInlineMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1045599379@qq.com");
        helper.setTo("1045599379@qq.com");
        helper.setSubject("主题：嵌入静态资源");
        helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);

        FileSystemResource file = new FileSystemResource(new File("src/main/resources/笑死.jpg"));
        // 这里需要注意的是addInline函数中资源名称weixin需要与正文中cid:weixin对应起来
        helper.addInline("weixin", file);

        mailSender.send(mimeMessage);
    }

    /**
     * 嵌入html邮件
     */
    @Test
    public void sendHtmlMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1045599379@qq.com");
        helper.setTo("1045599379@qq.com");
        helper.setSubject("邮件摸板测试"); // 标题
        // 处理邮件模板
        Context context = new Context();
        context.setVariable("code", 123456);
        String template = templateEngine.process("template", context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }

    /**
     * 模板邮件
     */
    @Test
    public void sendTemplateMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1045599379@qq.com");
        helper.setTo("1045599379@qq.com");
        helper.setSubject("主题：模板邮件");

        // Springboot版本过高这个VelocityEngineUtils没有勒
        /*String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "template.vm", "UTF-8", model);*/

        VelocityEngine velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "src/main/resources/templates");
        velocityEngine.init(p);

        Map<String, Object> model = new HashMap<>();
        model.put("username", "cmmplb");
        VelocityContext velocityContext = new VelocityContext(model);
        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate("template.vm", "UTF-8", velocityContext, writer);
        helper.setText(writer.toString(), true);
        mailSender.send(mimeMessage);
    }

    @Test
    public void contextLoads() {
        System.out.println("test");
    }
}
