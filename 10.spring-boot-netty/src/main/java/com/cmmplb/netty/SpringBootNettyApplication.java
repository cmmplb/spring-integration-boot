package com.cmmplb.netty;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootNettyApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootNettyApplication.class, args);
    }

}