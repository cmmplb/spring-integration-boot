package com.cmmplb.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;

/**
 * @author penglibo
 * @date 2021-08-12 09:27:34
 * @since jdk 1.8
 * 本地事务声明
 */

@Lazy
@Slf4j
@Role(2)
@Configuration
@ImportResource("classpath*:transaction/transaction.xml") // 配置文件事务声明
public class TransactionConfiguration {

}
