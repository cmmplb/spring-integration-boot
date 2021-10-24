package com.cmmplb.flyway;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author penglibo
 * @date 2021-05-19 13:52:04
 * @since jdk 1.8
 */

@SpringBootApplication
public class SpringBootFlywayApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootFlywayApplication.class, args);
    }

}
