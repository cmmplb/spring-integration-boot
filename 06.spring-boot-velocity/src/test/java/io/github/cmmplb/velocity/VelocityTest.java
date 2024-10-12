package io.github.cmmplb.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class VelocityTest {

    // @Autowired
    // private VelocityEngine velocityEngine;

    @Test
    public void velocityTest(){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "获取用户信息");
        model.put("name", "张三");
        model.put("age", "24");
        // Springboot版本过高这个VelocityEngineUtils没有勒
        // System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/index.vm", "UTF-8", model));

        VelocityEngine velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "src/main/resources/templates");
        velocityEngine.init(p);

        VelocityContext velocityContext = new VelocityContext(model);
        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate("index.vm", "UTF-8", velocityContext, writer);
        System.out.println(writer.toString());
    }
}
