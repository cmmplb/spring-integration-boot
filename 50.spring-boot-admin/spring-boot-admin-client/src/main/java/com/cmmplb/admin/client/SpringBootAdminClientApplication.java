package com.cmmplb.admin.client;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author penglibo
 * @createdate 2021-03-20
 */

@SpringBootApplication
public class SpringBootAdminClientApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootAdminClientApplication.class, args);
    }

}
