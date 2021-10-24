package com.xxl.job.executor;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class SpringBootXxlJobExecutorApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootXxlJobExecutorApplication.class, args);
    }

}