package com.cmmplb.data.jpa;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(DataJpaApplication.class, args);
    }
}
