package com.cmmplb;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author plb
 * @date 2021-01-06
 */

@SpringBootApplication
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(WebsocketApplication.class, args);
    }
}