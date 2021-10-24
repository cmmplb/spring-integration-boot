package com.xxl.job.admin;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */

@SpringBootApplication
public class SpringBootXxlJobAdminApplication {

	public static void main(String[] args) {
		SpringApplicationUtil.run(SpringBootXxlJobAdminApplication.class, args);
	}

}