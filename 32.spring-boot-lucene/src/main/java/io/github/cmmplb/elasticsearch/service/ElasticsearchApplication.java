package io.github.cmmplb.elasticsearch.service;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ElasticsearchApplication.class, args);
    }
}
