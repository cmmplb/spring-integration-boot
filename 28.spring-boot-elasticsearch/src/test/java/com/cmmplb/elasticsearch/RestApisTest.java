package com.cmmplb.elasticsearch;

import com.cmmplb.elasticsearch.service.ElasticsearchRestApisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author penglibo
 * @date 2023-08-18 09:27:23
 * @since jdk 1.8
 * RestApi方式操作
 */
@SpringBootTest
public class RestApisTest {

    @Autowired
    private ElasticsearchRestApisService elasticsearchRestApisService;

    // https://blog.csdn.net/qq_34777600/article/details/82142807
    // <REST Verb> <Node>:<Port>/<Index>/<Type>/<ID>
    // <REST Verb>：REST风格的语法谓词
    // <Node>:节点ip
    // <port>:节点端口号, 默认9200
    // <Index>:索引名
    // <Type>:文档类型 - 7.0以上只有一个_doc
    // <ID>:操作对象的ID号

    // api 地址：https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html

    @Test
    public void checkHealth() {
        // 检查集群
        elasticsearchRestApisService.checkHealth();
    }

    @Test
    public void createIndex() {
        // 创建索引
        elasticsearchRestApisService.createIndex();
    }

    @Test
    public void get() {
        // 获取
        elasticsearchRestApisService.get();
    }

    @Test
    public void delete() {
        // 删除
        elasticsearchRestApisService.delete();
    }

    @Test
    public void update() {
        // 更新
        elasticsearchRestApisService.update();
    }
}