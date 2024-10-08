package com.cmmplb.config.controller;

import com.cmmplb.config.beans.*;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.XmlUtils;
import io.github.cmmplb.core.utils.YmlUtil;
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
public class ConfigController {

    /**
     * 引用获取
     * @return name-version
     */
    @RequestMapping("/yml/util")
    public Result<String> getCustomProjectVersion() {
        return ResultUtil.success(YmlUtil.getApplicationValue("project.name") + "-" + YmlUtil.getApplicationValue("project.version"));
    }

    @Autowired
    private Environment environment;

    /**
     * 环境获取
     * @return name-version
     */
    @RequestMapping("/environment")
    public Result<String> getEnvironment() {
        return ResultUtil.success(environment.getProperty("environment.name") + "-" + environment.getProperty("environment.version"));
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
    public Result<String> getProjectVersion() {
        return ResultUtil.success(projectName + "-" + projectVersion);
    }

    // ---------------------------------------------

    @Autowired
    private ConfigBean configBean;

    /**
     * bean获取
     * @return name-version
     */
    @GetMapping("/bean")
    public Result<String> getBeanConfig() {
        return ResultUtil.success(configBean.getName() + "-" + configBean.getVersion());
    }

    // ---------------------------------------------

    @Value("#{'${version.list}'.split(',')}")
    private List<String> versionList;

    /**
     * 集合获取
     * @return versionList
     */
    @RequestMapping("/version/list")
    public Result<List<String>> getVersionList() {
        return ResultUtil.success(versionList);
    }

    // ---------------------------------------------

    @Autowired
    private ConfigProperties configProperties;

    /**
     * 属性获取
     * @return name-version
     */
    @RequestMapping("/properties")
    public Result<String> getProperties() {
        return ResultUtil.success(configProperties.getName() + "-" + configProperties.getVersion());
    }

    // ---------------------------------------------

    @Autowired
    private ConfigCustom configCustom;

    /**
     * 自定义配置
     * @return name-version
     */
    @RequestMapping("/custom/properties")
    public Result<String> getCustom() {
        return ResultUtil.success(configCustom.getName() + "-" + configCustom.getVersion());
    }

    // ---------------------------------------------

    @Autowired
    private Pom pom;

    /**
     * 读取pom
     * @return name-version
     */
    @RequestMapping("/pom")
    public Result<String> getPom() {
        return ResultUtil.success(pom.getName() + "-" + pom.getVersion());
    }

    // ---------------------------------------------

    /**
     * 读取自定义xml
     * @return name-version
     */
    @RequestMapping("/custom/xml")
    public Result<String> getConfigCustomXml() throws Exception {
        Resource resource = new ClassPathResource("static/custom.xml");
        // 利用输入流获取XML文件内容
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        // XML转为JAVA对象
        ConfigCustomXml configCustomXml = (ConfigCustomXml) XmlUtils.xmlStrToObject(ConfigCustomXml.class, buffer.toString());
        return ResultUtil.success(configCustomXml.getName() + "-" + configCustomXml.getVersion());
    }
}
