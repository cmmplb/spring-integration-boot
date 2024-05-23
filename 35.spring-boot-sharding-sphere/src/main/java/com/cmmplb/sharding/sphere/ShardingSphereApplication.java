package com.cmmplb.sharding.sphere;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication/*(exclude = DruidDataSourceAutoConfigure.class)*/
public class ShardingSphereApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ShardingSphereApplication.class, args);
    }
}
