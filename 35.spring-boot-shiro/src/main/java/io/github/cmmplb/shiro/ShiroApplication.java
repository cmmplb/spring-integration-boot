package io.github.cmmplb.shiro;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class ShiroApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ShiroApplication.class, args);
    }

}
