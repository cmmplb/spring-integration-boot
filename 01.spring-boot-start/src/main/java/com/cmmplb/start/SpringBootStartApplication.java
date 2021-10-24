package com.cmmplb.start;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @createdate 2021-03-20
 */

@SpringBootApplication
public class SpringBootStartApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootStartApplication.class, args);
    }

}
