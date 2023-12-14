package com.cmmplb.config;

import com.cmmplb.core.utils.SpringProfileUtil;
import com.cmmplb.core.utils.YmlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class ConfigTest {

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {
        System.out.println(environment.getProperty("environment.name") + "-" + environment.getProperty("environment.version"));
    }
}