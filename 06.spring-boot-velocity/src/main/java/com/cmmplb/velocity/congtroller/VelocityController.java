package com.cmmplb.velocity.congtroller;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author penglibo
 * @date 2021-11-17 15:32:33
 * @since jdk 1.8
 */

@RestController
public class VelocityController {

    @RequestMapping("/velocity")
    public String velocity() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "获取用户信息");
        model.put("name", "张三");
        model.put("age", "24");
        // Springboot版本过高这个VelocityEngineUtils没有勒
        // System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/index.vm", "UTF-8", model));

        VelocityEngine velocityEngine = new VelocityEngine();
        // 在Velocity中默认加载vm文件的类是FileResourceLoader, 而这个类是用File来读取vm文件的. 所以在不同的环境下, File的根目录是不一样的, 如在eclipse项目中就是项目的文件夹为根目录 . 
        //        解决vm找不到的方法我知道的有这两种：
        //        1. 设置把Velocity（RuntimeConstants）中的FILE_RESOURCE_LOADER_PATH对应的值设置为vm文件的绝对路径, 如：properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, "E:/project/velocity/vm/");
        //        2. 一般vm文件可以放在src目录下, 这样我们可以把默认加载vm文件类更改成ClasspathResourceLoader, ClasspathResourceLoader加载资源时是通过ClassLoader的getResourceAsStream方法. 
        //        可以这样修改：properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");这样就可以直接加载classpath目录下的vm文件了. 
        Properties p = new Properties();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "src/main/resources/templates");
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "/Users/penglibo/Cmmplb/Project/Cmmplb/Gitee/spring-integration-boot/06.spring-boot-velocity/src/main/resources/templates");
        velocityEngine.init(p);
        // p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // velocityEngine.addProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "src/main/resources/templates");

        VelocityContext velocityContext = new VelocityContext(model);
        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate("index.vm", "UTF-8", velocityContext, writer);
        return writer.toString();
    }

}
