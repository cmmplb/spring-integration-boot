package io.github.cmmplb.unit.test;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-20
 */

@SpringBootApplication
public class UnitTestApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(UnitTestApplication.class, args);
    }
}
