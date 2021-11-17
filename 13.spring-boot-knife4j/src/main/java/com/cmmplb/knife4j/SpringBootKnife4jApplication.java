package com.cmmplb.knife4j;

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
 * @date 2021-08-03 09:22:34
 * @since jdk 1.8
 */

@SpringBootApplication
public class SpringBootKnife4jApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootKnife4jApplication.class, args);
    }
}
