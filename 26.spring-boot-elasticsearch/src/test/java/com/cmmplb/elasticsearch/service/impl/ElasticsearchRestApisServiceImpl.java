package com.cmmplb.elasticsearch.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.cmmplb.elasticsearch.service.ElasticsearchRestApisService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2022-03-11 11:31:37
 * @since jdk 1.8
 */

@Service
public class ElasticsearchRestApisServiceImpl implements ElasticsearchRestApisService {

    private static String addr = "localhost:9200";

    /**
     * 检测集群是否健康。 确保9200端口号可用
     */
    @Override
    public void checkHealth() {
        String health = addr + "/_cat/health?v";
        System.out.println(HttpRequest.get(health).timeout(-1).execute().body());
    }

    /**
     * 创建索引=>REST-APIs方式
     */
    @Override
    public void createIndex() {
        // api 地址：https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html

        // rest请求创建索引==target 索引名称
        // PUT /<target>/_doc/<_id>
        // POST /<target>/_doc/
        // PUT /<target>/_create/<_id>
        // POST /<target>/_create/<_id>
        // 7.0以下有多个类型type，7.0以上只有一个type。叫_doc
        // 即: /索引名称/_doc(类型)/id
        // ***注意id 可以在请求路径后面，不加则自动生成
        // 要自动生成文档 ID，请使用请求格式并省略此参数。POST /<target>/_doc/
        // String createUrl = "localhost:9200/cmmplb/_create/1";
        String createUrl = addr + "/cmmplb/_doc/1?pretty";
        // pretty参数表示返回结果格式美观。

        // 其他参数
        // if_seq_no（可选，整数）仅当文档具有此序列号时才执行该操作。请参 阅开放式并发控制。
        // if_primary_term（可选，整数）仅当文档具有此主术语时才执行该操作。请参 阅开放式并发控制。
        // op_type（可选，枚举）设置为仅对尚不存在的文档编制索引（如果不存在则放置）。如果已存在具有指定项的文档，则索引操作将失败。与使用终结点相同。有效值：、。如果指定了文档 ID，则缺省为 。否则，它将缺省为 。create_id<index>/_createindexcreateindexcreate如果请求以数据流为目标，则需要 of。请参阅将文档添加到数据流。op_typecreate
        // pipeline（可选，字符串）用于预处理传入文档的管道的 ID。
        // refresh（可选，枚举）如果 ，则 Elasticsearch 刷新受影响的分片以使此操作对搜索可见，如果然后等待刷新以使此操作对搜索可见，则如果不执行任何刷新操作。有效值：、、。违约：。truewait_forfalsetruefalsewait_forfalse
        // routing（可选，字符串）用于将操作路由到特定分片的自定义值。
        // timeout（可选，时间单位）请求等待以下操作的时间段：自动创建索引、动态映射更新、等待活动分片、默认值为（一分钟）。这保证了Elasticsearch在失败之前至少等待超时。实际等待时间可能会更长，尤其是在发生多次等待时。1m
        // createUrl = createUrl + "&version=2";
        // version（可选，整数）用于并发控制的显式版本号。指定的版本必须与文档的当前版本匹配，请求才能成功。
        // version_type（可选，枚举）特定版本类型： ， .externalexternal_gte
        // wait_for_active_shards（可选，字符串）在继续操作之前必须处于活动状态的分片副本数。设置为 或任何正整数，直至索引中分片总数 （）。默认值：1，主分片。allnumber_of_replicas+1
        // require_alias（可选，布尔值）如果 为 ，则目标必须是索引别名。缺省值为 。truefalse

        // 添加field域
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("title", "什么是Elasticsearch?");
        map.put("content", "ElasticSearch是一个基于Lucene的搜索服务器");
        String result = HttpRequest.post(createUrl).body(JSON.toJSONString(map)).timeout(-1).execute().body();
        System.out.println(result);
    }

    @Override
    public void get() {
        // GET <index>/_doc/<_id>
        // HEAD <index>/_doc/<_id>
        // GET <index>/_source/<_id>
        // HEAD <index>/_source/<_id>
        String getUrl = addr + "/cmmplb/_doc/1?pretty";
        String result = HttpRequest.get(getUrl).timeout(-1).execute().body();
        System.out.println(result);
    }

    @Override
    public void delete() {
        // DELETE /<index>/_doc/<_id>
        String deleteUrl = addr + "/cmmplb/_doc/yRHHd38B3Up-yZTczU-U";
        HttpRequest.delete(deleteUrl).timeout(-1).execute().body();
    }

    @Override
    public void update() {
        // POST /<index>/_update/<_id>
        String updateUrl = addr + "/cmmplb/_update/1";
        // 添加field域
        Map<String, Object> map1 = new HashMap<>();

        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("title", "啊?");
        map.put("content", "搜索服务器");

        map1.put("doc",map);
        System.out.println("============================");
        System.out.println("============================");
        System.out.println("============================");
        System.out.println("============================");
        System.out.println(HttpRequest.post(updateUrl).body(JSON.toJSONString(map1)).timeout(-1).execute().body());
    }


}
