package io.github.cmmplb.cache.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-04-10 16:51:11
 */

@Configuration
@MapperScan(basePackages = {"com.cmmplb.cache.mapper"})
public class MybatisAutoConfiguration {

}