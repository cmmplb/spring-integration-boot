package com.cmmplb.mybatis.plugin.propertise;

import io.github.cmmplb.core.constants.StringConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author penglibo
 * @date 2021-08-18 10:28:28
 * @since jdk 1.8
 */

@Data
// @Component // 如果不想放入容器, 就在引用的地方添加@EnableConfigurationProperties(LockerProperties.class)
@ConfigurationProperties(prefix = StringConstant.LOCKER)
public class LockerProperties {

    /**
     * 数据库字段
     */
    private String column;

    /**
     * 实体类字段
     */
    private String property;

    /**
     * 失败重试次数
     */
    private Integer retryNumber;
}
