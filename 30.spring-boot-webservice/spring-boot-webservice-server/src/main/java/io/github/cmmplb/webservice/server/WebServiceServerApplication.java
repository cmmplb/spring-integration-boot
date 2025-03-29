package io.github.cmmplb.webservice.server;


import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author plb
 * @date 2021-01-06
 */

@SpringBootApplication
public class WebServiceServerApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(WebServiceServerApplication.class, args);
    }
}