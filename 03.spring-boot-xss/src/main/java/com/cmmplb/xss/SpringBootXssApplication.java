package com.cmmplb.xss;

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
 * @date 2021-08-27 17:44:36
 * @since jdk 1.8
 */

@SpringBootApplication
public class SpringBootXssApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootXssApplication.class, args);
    }
}
