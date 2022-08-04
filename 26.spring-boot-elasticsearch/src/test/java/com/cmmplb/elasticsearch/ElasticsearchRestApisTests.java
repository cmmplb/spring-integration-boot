package com.cmmplb.elasticsearch;

import com.cmmplb.elasticsearch.service.ElasticsearchRestApisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ElasticsearchRestApisTests {

    @Autowired
    private ElasticsearchRestApisService elasticsearchRestApisService;

    // https://blog.csdn.net/qq_34777600/article/details/82142807
    // <REST Verb> <Node>:<Port>/<Index>/<Type>/<ID>
    // <REST Verb>：REST风格的语法谓词
    // <Node>:节点ip
    // <port>:节点端口号，默认9200
    // <Index>:索引名
    // <Type>:文档类型 - 7.0以上只有一个_doc
    // <ID>:操作对象的ID号

    // api 地址：https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html

    @Test
    public void init() {
        // 检查集群
        // elasticsearchRestApisService.checkHealth();
        // 创建索引
        // elasticsearchRestApisService.createIndex();
        // 获取
        // elasticsearchRestApisService.get();
        // 删除
        // elasticsearchRestApisService.delete();
        // 更新
        elasticsearchRestApisService.update();
    }
}