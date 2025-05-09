package io.github.cmmplb.sync;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-05-19 13:52:04
 * @since jdk 1.8
 */

@SpringBootApplication
public class SyncApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SyncApplication.class, args);
    }

}
