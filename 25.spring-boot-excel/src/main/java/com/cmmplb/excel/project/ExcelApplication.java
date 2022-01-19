package com.cmmplb.excel.project;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class ExcelApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ExcelApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ExcelApplication.class);
        builder.properties("spring.config.name:application", "spring.config.additional-location:classpath:/config/");
    }
}
