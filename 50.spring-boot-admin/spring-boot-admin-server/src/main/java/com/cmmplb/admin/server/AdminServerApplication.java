package com.cmmplb.admin.server;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-20
 */

@SpringBootApplication
public class AdminServerApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(AdminServerApplication.class, args);
    }

}
