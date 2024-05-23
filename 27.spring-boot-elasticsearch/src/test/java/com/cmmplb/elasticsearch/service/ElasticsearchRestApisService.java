package com.cmmplb.elasticsearch.service;

import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2022-03-11 11:31:04
 * @since jdk 1.8
 */

public interface ElasticsearchRestApisService {

    /**
     * 检测集群是否健康。 确保9200端口号可用
     */
    void checkHealth();

    /**
     * 创建索引=>REST-APIs方式
     */
    void createIndex();

    /**
     * 获取
     */
    void get();

    /**
     * 删除
     */
    void delete();

    /**
     * 更新
     */
    void update();
}
