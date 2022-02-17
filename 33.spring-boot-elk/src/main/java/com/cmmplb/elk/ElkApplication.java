package com.cmmplb.elk;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class ElkApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ElkApplication.class, args);
    }
}
