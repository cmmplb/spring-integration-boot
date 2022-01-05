package com.cmmplb.excel.project;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class SpringBootExcelApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootExcelApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootExcelApplication.class);
        builder.properties("spring.config.name:application", "spring.config.additional-location:classpath:/config/");
    }
}
