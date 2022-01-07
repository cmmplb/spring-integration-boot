package com.cmmplb.config.controller;

import com.cmmplb.config.beans.*;
import com.cmmplb.core.utils.XmlBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-13 15:00:31
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/")
public class SpringBootConfigController {

    @Autowired
    private Environment environment;

    /**
     * 环境获取
     * @return name-version
     */
    @RequestMapping("/environment")
    public String getEnvironment() {
        return environment.getProperty("environment.name") + "-" + environment.getProperty("environment.version");
    }

    @Value("${project.name}")
    private String projectName;
    @Value("${project.version}")
    private String projectVersion;

    /**
     * 引用获取
     * @return name-version
     */
    @RequestMapping("/project/version")
    public String getProjectVersion() {
        return projectName + "-" + projectVersion;
    }

    // ---------------------------------------------

    @Autowired
    private ConfigBean configBean;

    /**
     * bean获取
     * @return name-version
     */
    @GetMapping("/bean")
    public String getBeanConfig() {
        return configBean.getName() + "-" + configBean.getVersion();
    }

    // ---------------------------------------------

    @Value("#{'${version.list}'.split(',')}")
    private List<String> versionList;

    /**
     * 集合获取
     * @return versionList
     */
    @RequestMapping("/version/list")
    public List<String> getVersionList() {
        return versionList;
    }

    // ---------------------------------------------

    @Autowired
    private ConfigProperties configProperties;

    /**
     * 属性获取
     * @return name-version
     */
    @RequestMapping("/properties")
    public String getProperties() {
        return configProperties.getName() + "-" + configProperties.getVersion();
    }

    // ---------------------------------------------

    @Autowired
    private ConfigCustom configCustom;

    /**
     * 自定义配置
     * @return name-version
     */
    @RequestMapping("/custom/properties")
    public String getCustom() {
        return configCustom.getName() + "-" + configCustom.getVersion();
    }

    // ---------------------------------------------

    @Autowired
    private Pom pom;

    /**
     * 读取pom
     * @return name-version
     */
    @RequestMapping("/pom")
    public String getPom() {
        return pom.getName() + "-" + pom.getVersion();
    }

    // ---------------------------------------------

    /**
     * 读取自定义xml
     * @return name-version
     */
    @RequestMapping("/custom/xml")
    public String getConfigCustomXml() throws Exception {
        Resource resource = new ClassPathResource("static/custom.xml");
        //利用输入流获取XML文件内容
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        //XML转为JAVA对象
        ConfigCustomXml configCustomXml = (ConfigCustomXml) XmlBuilderUtil.xmlStrToObject(ConfigCustomXml.class, buffer.toString());
        return configCustomXml.getName() + "-" + configCustomXml.getVersion();
    }
}
