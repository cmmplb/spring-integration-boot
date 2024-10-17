package io.github.cmmplb.mail.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author penglibo
 * @date 2021-09-14 12:00:49
 * @since jdk 1.8
 */

@Tag(name = "邮件发送演示")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("send")
public class SendController {

    @Resource
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 简单邮件
     */
    @PostMapping("/simple/mail")
    @Operation(summary = "简单邮件", description = "简单邮件")
    @ApiOperationSupport(order = 1, author = "李四")
    public Result<Boolean> sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1045599379@qq.com");
        message.setTo("1045599379@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
        return ResultUtil.success(true);
    }

    /**
     * 发送附件
     */
    @PostMapping("/attachments/mail")
    @Operation(summary = "发送附件", description = "发送附件")
    @ApiOperationSupport(order = 2)
    public Result<Boolean> sendAttachmentsMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("1045599379@qq.com");
        messageHelper.setTo("1045599379@qq.com");
        messageHelper.setSubject("主题：有附件");
        messageHelper.setText("有附件的邮件");
        InputStream inputStream = FileUtil.getInputStream("test/笑死1.jpg");
        // 单元测试main文件路径不一样
        // FileSystemResource file = new FileSystemResource(new File("src/main/resources/笑死1.jpg"));
        // FileSystemResource file = new FileSystemResource("src/main/resources/笑死1.jpg");
        // Passed-in Resource contains an open stream: invalid argument. JavaMail requires an InputStreamSource that creates a fresh stream for every call.
        // 流不匹配会导致报错. 通过转换成对应的流即可解决
        // messageHelper.addAttachment("附件-1.jpg", file);
        // messageHelper.addAttachment("附件-2.jpg", file);
        // messageHelper.addAttachment(MimeUtility.encodeWord("附件-1.jpg"), new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));
        // messageHelper.addAttachment(MimeUtility.encodeWord("附件-2.jpg"), new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));

        // messageHelper.addAttachment("附件-3.jpg", new InputStreamResource(inputStream));
        // messageHelper.addAttachment("附件-4.jpg", new InputStreamResource(inputStream));

        messageHelper.addAttachment("附件-3.jpg", new ByteArrayResource(IOUtils.toByteArray(inputStream)));
        // 注意, 这个inputStream再次使用会拿不到文件, 已经被读取
        messageHelper.addAttachment("附件-4.jpg", new ByteArrayResource(IOUtils.toByteArray(inputStream)));

        mailSender.send(mimeMessage);
        return ResultUtil.success(true);
    }

    /**
     * 嵌入静态资源
     */
    @PostMapping("/inline/mail")
    @Operation(summary = "嵌入静态资源", description = "嵌入静态资源")
    @ApiOperationSupport(order = 3)
    public Result<Boolean> sendInlineMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1045599379@qq.com");
        helper.setTo("1045599379@qq.com");
        helper.setSubject("主题：嵌入静态资源");
        helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);


        InputStream inputStream = FileUtil.getInputStream("笑死.jpg");


        // helper.addInline("weixin", new ByteArrayResource(IOUtils.toByteArray(inputStream)));

        helper.addInline("weixin", new ClassPathResource("笑死.jpg"));

        // content-types.properties
        // helper.addInline("weixin", new InputStreamResource(inputStream), "image/jpeg");

        // 这里需要注意的是addInline函数中资源名称weixin需要与正文中cid:weixin对应起来
        // FileSystemResource file = new FileSystemResource(new File("src/main/resources/test/笑死1.jpg"));
        // helper.addInline("weixin", file);

        // File file2 = new File("/Users/penglibo/Cmmplb/Project/Cmmplb/Gitee/spring-integration-boot/10.spring-boot-mail/src/main/resources/test/笑死1.jpg");
        // helper.addInline("weixin", file2);

        mailSender.send(mimeMessage);
        return ResultUtil.success(true);
    }

    /**
     * 嵌入html邮件
     */
    @PostMapping("/html/mail")
    @Operation(summary = "嵌入html邮件", description = "嵌入html邮件")
    @ApiOperationSupport(order = 4)
    public Result<Boolean> sendHtmlMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1045599379@qq.com");
        helper.setTo("1045599379@qq.com");
        // 标题
        helper.setSubject("邮件摸板测试");
        // 处理邮件模板
        Context context = new Context();
        context.setVariable("code", 123456);
        String template = templateEngine.process("template", context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
        return ResultUtil.success(true);
    }

    /**
     * 模板邮件
     */
    @PostMapping("/template/mail")
    @Operation(summary = "模板邮件", description = "模板邮件")
    @ApiOperationSupport(order = 5)
    public Result<Boolean> sendTemplateMail() throws Exception {

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
        // 在Velocity中默认加载vm文件的类是FileResourceLoader, 而这个类是用File来读取vm文件的. 所以在不同的环境下, File的根目录是不一样的, 如在eclipse项目中就是项目的文件夹为根目录 .
        //        解决vm找不到的方法我知道的有这两种：
        //        1. 设置把Velocity（RuntimeConstants）中的FILE_RESOURCE_LOADER_PATH对应的值设置为vm文件的绝对路径, 如：properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, "E:/project/velocity/vm/");
        //        2. 一般vm文件可以放在src目录下, 这样我们可以把默认加载vm文件类更改成ClasspathResourceLoader, ClasspathResourceLoader加载资源时是通过ClassLoader的getResourceAsStream方法.
        //        可以这样修改：properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");这样就可以直接加载classpath目录下的vm文件了.
        // p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "src/main/resources/templates");
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "/Users/penglibo/Cmmplb/Project/Cmmplb/Gitee/spring-integration-boot/10.spring-boot-mail/src/main/resources/templates");
        // p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init(p);

        Map<String, Object> model = new HashMap<>();
        model.put("username", "cmmplb");
        VelocityContext velocityContext = new VelocityContext(model);
        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate("template.vm", "UTF-8", velocityContext, writer);
        helper.setText(writer.toString(), true);
        mailSender.send(mimeMessage);
        return ResultUtil.success(true);
    }

}