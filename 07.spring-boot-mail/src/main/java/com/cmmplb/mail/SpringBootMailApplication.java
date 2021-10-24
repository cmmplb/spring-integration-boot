package com.cmmplb.mail;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-05-19 13:52:04
 * @since jdk 1.8
 */

@SpringBootApplication
public class SpringBootMailApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootMailApplication.class, args);
    }

}
