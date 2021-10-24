package com.cmmplb.freemarker;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-08-27 17:44:36
 * @since jdk 1.8
 */

@SpringBootApplication
public class SpringBootFreemarkerApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootFreemarkerApplication.class, args);
    }
}
