package io.github.cmmplb.thymeleaf;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-20
 */

@SpringBootApplication
public class ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ThymeleafApplication.class, args);
    }

}
