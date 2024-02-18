package com.cmmplb.mybatis.mapper.config;

import com.cmmplb.core.constants.StringConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author penglibo
 * @date 2021-08-12 09:27:34
 * @since jdk 1.8
 * see {@link transaction/transaction.xml:29}
 * 配置文件方式声明事务，扫描业务路径: com.cmmplb.mybatis.service..*.*(..)
 */

@Configuration
@ImportResource(StringConstants.TRANSACTION_LOCATIONS)
public class TransactionConfiguration {

}
