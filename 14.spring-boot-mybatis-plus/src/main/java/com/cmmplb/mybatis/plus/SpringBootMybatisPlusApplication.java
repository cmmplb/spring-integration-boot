package com.cmmplb.mybatis.plus;

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
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class SpringBootMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootMybatisPlusApplication.class, args);
    }
}
