package com.cmmplb.netty;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(NettyApplication.class, args);
    }

}