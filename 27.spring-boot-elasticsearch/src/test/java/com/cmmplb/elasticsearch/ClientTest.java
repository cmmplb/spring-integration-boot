package com.cmmplb.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.cmmplb.core.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

/**
 * @author penglibo
 * @date 2023-08-18 09:27:23
 * @since jdk 1.8
 * 客户端方式操作
 */

@Slf4j
@SpringBootTest
public class ClientTest {

    private final static String INDEX_NAME = "rest-client-one";

    // 创建ES客户端
    private final static RestHighLevelClient CLIENT = getClient();

    public static void main(String[] args) {
        // 客户端方式创建索引
        // testCreateIndex();
        // 建立Mapping
        // testMapping();
        // 判断索引是否存在
        // exist();
        // 新增
        // insert();
        // 批量新增
        // batchInsert();
        // 删除索引
        // delete();
        // 删除单条
        // deleteById();
        // 批量删除
        // batchDelete();
        // 更新
        // update();
        // 查询索引
        // get();
        // 根据id查询
        // getById();
        // 查询索引中全部的数据
        // matchAllQuery();
        // 条件查询
        // termQuery();
        // 模糊查询（通配符查询）
        // wildcardQuery();
        // 分页 排序 过滤字段 查询
        // pagedQuery();
        // 组合查询
        // matchQuery();
        // 范围查询
        // rangeQuery();
        // 模糊查询
        // fuzzyQuery();
        // 高亮查询
        // highlightBuilderQuery();
        // 聚合查询
        // aggregationBuilder();
        // 分组查询
        groupAggregationBuilder();
    }

    // 客户端方式创建索引
    public static void testCreateIndex() {
        // 创建索引
        CreateIndexResponse res;
        try {
            res = CLIENT.indices().create(new CreateIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 响应状态
        log.info("acknowledged:{}", res.isAcknowledged());
        // 关闭ES客户端
        close();
    }

    // 建立Mapping
    public static void testMapping() {
        AcknowledgedResponse acknowledgedResponse;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("dynamic", "true")
                        //设置type中的属性
                        .startObject("properties")
                            .startObject("name")
                                .field("type", "text")  // 指定数据类型
                                .field("store", false)  // 是否存储(默认就是false),使用默认的_score来存储
                                .field("index", true)   // 是否创建索引，默认true
                                .field("analyzer", "ik_smart")   // 使用ik最小切分方式作为分词器
                            .endObject()

                            .startObject("age")
                                .field("type", "text")  // 指定数据类型
                                .field("store", false)  // 是否存储(默认就是false),使用默认的_score来存储
                                .field("index", true)   // 是否创建索引，默认true
                                .field("analyzer", "ik_smart")   // 使用ik最小切分方式作为分词器
                            .endObject()
                        .endObject()
                    .endObject();
            acknowledgedResponse = CLIENT.indices().putMapping(new PutMappingRequest(INDEX_NAME).source(builder), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 响应状态
        log.info("acknowledgedResponse:{}", acknowledgedResponse.isAcknowledged());
        // 关闭ES客户端
        close();
    }

    // 判断索引是否存在
    public static void exist() {
        GetIndexRequest request = new GetIndexRequest("test_index");
        boolean flag;
        try {
            flag = CLIENT.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("-------------test_index exists：" + flag + "----------------------");
        // 关闭ES客户端
        close();
    }

    // 新增
    public static void insert() {
        // 插入数据
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhang san");
        map.put("sex", "男");
        map.put("tel", "15234456234");
        // 向ES插入数据，必须将数据转换为JSON格式
        IndexRequest indexRequest = new IndexRequest().index(INDEX_NAME).id("1").source(JSON.toJSONString(map), XContentType.JSON);

        IndexResponse indexResponse;
        try {
            indexResponse = CLIENT.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 插入响应状态（CREATED:插入成功）UPDATED
        log.info("result:{}", indexResponse.getResult());
        // 关闭ES客户端
        close();
    }

    // 批量新增
    public static void batchInsert() {
        // 批量插入数据
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 100000; i++) {
            bulkRequest.add(new IndexRequest().index(INDEX_NAME).id(String.valueOf(i)).source(XContentType.JSON
                    , "name", RandomUtil.getRandomChineseName()
                    , "age", new Random().nextInt(100)
                    , "sex", new Random().nextInt(2) - 1 == 0 ? "女" : "男"
            ));
        }
        BulkResponse bulkResponse;
        try {
            bulkResponse = CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("took:{}", bulkResponse.getTook());
        BulkItemResponse[] items = bulkResponse.getItems();
        log.info("items.length:{}", items.length);
        close();
    }

    // 删除索引
    public static void delete() {
        AcknowledgedResponse deleteIndexResponse;
        try {
            deleteIndexResponse = CLIENT.indices().delete(new DeleteIndexRequest("cmmplb"), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean acknowledged = deleteIndexResponse.isAcknowledged();// 响应状态
        log.info("acknowledged:{}", acknowledged);
        // 关闭ES客户端
        close();
    }

    // 删除单条
    public static void deleteById() {
        DeleteResponse deleteResponse;
        try {
            deleteResponse = CLIENT.delete(new DeleteRequest().index(INDEX_NAME).id("1"), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DocWriteResponse.Result result = deleteResponse.getResult();
        log.info("result:{}", result);
        // 关闭ES客户端
        close();
    }

    // 批量删除
    public static void batchDelete() {
        BulkRequest bulkRequest = new BulkRequest();
        // 批量删除数据
        bulkRequest.add(new DeleteRequest().index(INDEX_NAME).id("1001"));
        bulkRequest.add(new DeleteRequest().index(INDEX_NAME).id("1002"));
        BulkResponse bulkResponse;
        try {
            bulkResponse = CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("took:{}", bulkResponse.getTook());
        BulkItemResponse[] items = bulkResponse.getItems();
        log.info("items.length:{}", items.length);
        // 关闭ES客户端
        close();
    }

    // 更新
    public static void update() {
        // 修改数据
        UpdateRequest updateRequest = new UpdateRequest().index(INDEX_NAME).id("1003");
        updateRequest.doc(XContentType.JSON, "sex", "女");
        UpdateResponse updateResponse;
        try {
            updateResponse = CLIENT.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DocWriteResponse.Result updateResponseResult = updateResponse.getResult();// 修改响应状态（CREATED:修改成功）
        log.info("updateResponseResult:{}", updateResponseResult);
        // 关闭ES客户端
        close();
    }

    // 查询索引
    public static void get() {
        GetIndexResponse getIndexResponse;
        try {
            getIndexResponse = CLIENT.indices().get(new GetIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, List<AliasMetadata>> aliases = getIndexResponse.getAliases();// 别名
        Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();// 配置 机构
        Map<String, Settings> settings = getIndexResponse.getSettings();
        log.info("aliases:{}", aliases);
        log.info("mappings:{}", mappings);
        log.info("settings:{}", settings);
        Map<String, String> dataStreams = getIndexResponse.getDataStreams();
        log.info("dataStreams:{}", dataStreams);
        // 关闭ES客户端
        close();
    }

    // 根据id查询
    public static void getById() {
        GetResponse getResponse;
        try {
            getResponse = CLIENT.get(new GetRequest().index(INDEX_NAME).id("16"), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String sourceAsString = getResponse.getSourceAsString();
        log.info("sourceAsString:{}", sourceAsString);
        // 关闭ES客户端
        close();
    }

    // 查询索引中全部的数据
    public static void matchAllQuery() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse searchResponse;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        log.info("totalHits:{}", totalHits);
        TimeValue took = searchResponse.getTook();
        log.info("took:{}", took);
        SearchHit[] searchHits = hits.getHits();
        log.info("searchHits:{}", Arrays.toString(searchHits));
        // 关闭ES客户端
        close();
    }

    // 条件查询-条件分词，条件不分词
    public static void termQuery() {
        // 条件查询 termQuery
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        // 字符串查询(条件分词)
        // QueryBuilders.queryStringQuery("搜索")默认匹配所有域
        // 如果如果添加.field("name")：表示只在name字段进行搜索
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("宋雪").field("name");
        // searchRequest.source(new SearchSourceBuilder().query(queryStringQueryBuilder));

        // 字符串查询(条件不分词)
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "宋雪");
        // 搜不到结果：为什么？没有使用分词器，默认的分词器会单独分成每个字查询
        searchRequest.source(new SearchSourceBuilder().query(termQueryBuilder));
        SearchResponse searchResponse;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 模糊查询（通配符查询）
    public static void wildcardQuery() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        // *：表示所有的任意的多个字符组成
        // ?：表示1个任意的字符
        WildcardQueryBuilder wildcarded = QueryBuilders.wildcardQuery("name", "宋*");

        searchRequest.source(new SearchSourceBuilder().query(wildcarded));
        SearchResponse searchResponse;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 分页 排序 过滤字段 查询
    public static void pagedQuery() {
        // 分页 排序 过滤字段 查询
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //  （当前页码-1）*每页显示条数
        builder.from(1);// 分页用
        builder.size(100);// 分页用
        // builder.sort("age", SortOrder.DESC);// 排序用
        String[] excludes = {};// 排除的内容
        String[] includes = {"name","age"};// 包含的内容
        builder.fetchSource(includes, excludes);// 过滤字段查询
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest.source(builder), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 组合查询
    public static void matchQuery() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));// 满足这个条件
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));// 不满足这个条件
        // --------------------------满足30或者满足40------------------------------------
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 40));
        //  （当前页码-1）*每页显示条数
        builder.from(1);// 分页用
        builder.size(100);// 分页用
        searchRequest.source(builder.query(boolQueryBuilder));
        // -----------------------------------------------------------------------------
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 范围查询
    public static void rangeQuery() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 范围查询
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.gte(30);//>=
        rangeQuery.lte(40);//<=
        searchRequest.source(builder.query(rangeQuery));

        // ---------------------------------------------------------------------------
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 模糊查询
    public static void fuzzyQuery() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // -----------------------------------------------------------------------------
        // 模糊查询
        searchRequest.source(builder.query(QueryBuilders.fuzzyQuery("name", "冉").fuzziness(Fuzziness.ONE)));
        // -----------------------------------------------------------------------------
        // ---------------------------------------------------------------------------
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 高亮查询
    public static void highlightBuilderQuery() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 高亮查询
        TermsQueryBuilder termQueryBuilder = QueryBuilders.termsQuery("name", "张");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red' >");// 添加前缀
        highlightBuilder.postTags("</font>");// 添加后缀
        highlightBuilder.field("name");// 高亮标签内容
        builder.highlighter(highlightBuilder);
        builder.query(termQueryBuilder);
        searchRequest.source(builder);
        // ---------------------------------------------------------------------------
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 聚合查询
    public static void aggregationBuilder() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // -----------------------------------------------------------------------------
        // 聚合查询
        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("name");
        builder.aggregation(aggregationBuilder);
        searchRequest.source(builder);
        // ---------------------------------------------------------------------------
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    // 分组查询
    public static void groupAggregationBuilder() {
        SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // ----------------------------------------------------------------------------
        // 分组查询
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);
        searchRequest.source(builder);
        // ---------------------------------------------------------------------------
        SearchResponse searchResponse = null;
        try {
            searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = searchResponse.getHits();
        log.info("总记录数：" + hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            log.info("json: " + hit.getSourceAsString());
            log.info("获取name:" + hit.getSourceAsMap().get("name"));
            log.info("-------------------------------------------------");
        }
        // 关闭ES客户端
        close();
    }

    private static RestHighLevelClient getClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9200, "http")
                )
        );
    }

    /*
     * 释放资源
     */
    public static void close() {
        try {
            CLIENT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
