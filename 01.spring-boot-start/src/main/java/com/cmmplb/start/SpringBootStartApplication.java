package com.cmmplb.start;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-20
 */

@Slf4j
@SpringBootApplication
public class SpringBootStartApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootStartApplication.class, args);
        System.out.println(log.isDebugEnabled());
    }

}
