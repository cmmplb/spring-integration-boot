package com.cmmplb.mybatis;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class MybatisApplication {

    static {
        // 解决清空空闲60秒以上的连接的警告-WARN c.a.druid.pool.DruidAbstractDataSource   : discard long time none received connection
        System.setProperty(StringConstants.DRUID_MYSQL_USE_PING_METHOD, StringConstants.FALSE);
    }

    public static void main(String[] args) {
        SpringApplicationUtil.run(MybatisApplication.class, args);
    }
}
