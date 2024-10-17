package io.github.cmmplb.elasticsearch.service.controller.demo;// package com.cmmplb.elasticsearch.controller.demo;
//
// import com.alibaba.fastjson.JSON;
// import com.cmmplb.core.constants.StringConstant;
// import com.cmmplb.core.utils.RandomUtil;
// import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
// import com.github.xiaoymin.knife4j.annotations.ApiSort;
// import com.github.xiaoymin.knife4j.annotations.ApiSupport;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.http.HttpHost;
// import org.apache.lucene.search.TotalHits;
// import org.elasticsearch.action.DocWriteResponse;
// import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
// import org.elasticsearch.action.bulk.BulkItemResponse;
// import org.elasticsearch.action.bulk.BulkRequest;
// import org.elasticsearch.action.bulk.BulkResponse;
// import org.elasticsearch.action.delete.DeleteRequest;
// import org.elasticsearch.action.delete.DeleteResponse;
// import org.elasticsearch.action.get.GetRequest;
// import org.elasticsearch.action.get.GetResponse;
// import org.elasticsearch.action.index.IndexRequest;
// import org.elasticsearch.action.index.IndexResponse;
// import org.elasticsearch.action.search.SearchRequest;
// import org.elasticsearch.action.search.SearchResponse;
// import org.elasticsearch.action.support.master.AcknowledgedResponse;
// import org.elasticsearch.action.update.UpdateRequest;
// import org.elasticsearch.action.update.UpdateResponse;
// import org.elasticsearch.client.RequestOptions;
// import org.elasticsearch.client.RestClient;
// import org.elasticsearch.client.RestHighLevelClient;
// import org.elasticsearch.client.indices.CreateIndexRequest;
// import org.elasticsearch.client.indices.CreateIndexResponse;
// import org.elasticsearch.client.indices.GetIndexRequest;
// import org.elasticsearch.client.indices.GetIndexResponse;
// import org.elasticsearch.cluster.metadata.AliasMetadata;
// import org.elasticsearch.cluster.metadata.MappingMetadata;
// import org.elasticsearch.common.settings.Settings;
// import org.elasticsearch.common.unit.Fuzziness;
// import org.elasticsearch.core.TimeValue;
// import org.elasticsearch.index.query.*;
// import org.elasticsearch.search.SearchHit;
// import org.elasticsearch.search.SearchHits;
// import org.elasticsearch.search.aggregations.AggregationBuilder;
// import org.elasticsearch.search.aggregations.AggregationBuilders;
// import org.elasticsearch.search.builder.SearchSourceBuilder;
// import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
// import org.elasticsearch.xcontent.XContentType;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.io.IOException;
// import java.util.*;
//
// /**
//  * @author penglibo
//  * @date 2023-09-04 09:42:44
//  * @since jdk 1.8
//  */
//
// @Slf4j
// @RestController
// @Tag(name = "7.0版本连接方式")
// // @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
// @ApiSort(1)
// // 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
// @ApiSupport(order = 1, author = StringConstant.AUTHOR)
// @RequestMapping("/client/v7.0")
// public class Client7Controller {
//
//     private final String INDEX_NAME = "rest-client";
//
//     /**
//      * 创建ES客户端
//      */
//     private final static RestHighLevelClient CLIENT = getClient();
//
//     @GetMapping("/create/index")
//     @ApiOperationSupport(order = 1)
//     @Operation(summary = "创建索引", description = "创建索引")
//     public void createIndex() {
//         // 创建索引
//         CreateIndexResponse res;
//         try {
//             res = CLIENT.indices().create(new CreateIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         // 响应状态
//         log.info("acknowledged:{}", res.isAcknowledged());
//     }
//
//     @GetMapping("/exist")
//     @ApiOperationSupport(order = 2)
//     @Operation(summary = "判断索引是否存在")
//     public void exist() {
//         boolean flag;
//         try {
//             GetIndexRequest request = new GetIndexRequest("test_index");
//             RequestOptions aDefault = RequestOptions.DEFAULT;
//             flag = CLIENT.indices().exists(request,aDefault );
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         log.info("-------------test_index exists：" + flag + "----------------------");
//     }
//
//     @GetMapping("/insert")
//     @Operation(summary = "新增")
//     @ApiOperationSupport(order = 3)
//     public void insert() {
//         // 插入数据
//         Map<String, Object> map = new HashMap<>();
//         map.put("name", "zhang san");
//         map.put("sex", "男");
//         map.put("tel", "15234456234");
//         // 向ES插入数据, 必须将数据转换为JSON格式
//         IndexRequest indexRequest = new IndexRequest().index(INDEX_NAME).id("1").source(JSON.toJSONString(map), XContentType.JSON);
//
//         IndexResponse indexResponse;
//         try {
//             indexResponse = CLIENT.index(indexRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         // 插入响应状态（CREATED:插入成功）UPDATED
//         log.info("result:{}", indexResponse.getResult());
//     }
//
//     @GetMapping("/batch/insert")
//     @Operation(summary = "批量新增")
//     @ApiOperationSupport(order = 4)
//     public void batchInsert() {
//         // 批量插入数据
//         BulkRequest bulkRequest = new BulkRequest();
//         for (int i = 0; i < 100000; i++) {
//             bulkRequest.add(new IndexRequest().index(INDEX_NAME).id(String.valueOf(i)).source(XContentType.JSON
//                     , "name", RandomUtil.getRandomChineseName()
//                     , "age", new Random().nextInt(100)
//                     , "sex", new Random().nextInt(2) - 1 == 0 ? "女" : "男"
//             ));
//         }
//         BulkResponse bulkResponse;
//         try {
//             bulkResponse = CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         log.info("took:{}", bulkResponse.getTook());
//         BulkItemResponse[] items = bulkResponse.getItems();
//         log.info("items.length:{}", items.length);
//     }
//
//     @GetMapping("/delete")
//     @Operation(summary = "删除索引")
//     @ApiOperationSupport(order = 5)
//     public void delete() {
//         AcknowledgedResponse deleteIndexResponse;
//         try {
//             deleteIndexResponse = CLIENT.indices().delete(new DeleteIndexRequest("cmmplb"), RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         boolean acknowledged = deleteIndexResponse.isAcknowledged();
//         log.info("acknowledged:{}", acknowledged);
//     }
//
//     @GetMapping("/delete/id")
//     @Operation(summary = "删除单条")
//     @ApiOperationSupport(order = 5)
//     public void deleteById() {
//         DeleteResponse deleteResponse;
//         try {
//             deleteResponse = CLIENT.delete(new DeleteRequest().index(INDEX_NAME).id("1"), RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         DocWriteResponse.Result result = deleteResponse.getResult();
//         log.info("result:{}", result);
//     }
//
//     @GetMapping("/batch/delete")
//     @Operation(summary = "批量删除")
//     @ApiOperationSupport(order = 6)
//     public void batchDelete() {
//         BulkRequest bulkRequest = new BulkRequest();
//         // 批量删除数据
//         bulkRequest.add(new DeleteRequest().index(INDEX_NAME).id("1001"));
//         bulkRequest.add(new DeleteRequest().index(INDEX_NAME).id("1002"));
//         BulkResponse bulkResponse;
//         try {
//             bulkResponse = CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         log.info("took:{}", bulkResponse.getTook());
//         BulkItemResponse[] items = bulkResponse.getItems();
//         log.info("items.length:{}", items.length);
//     }
//
//     @GetMapping("/update")
//     @Operation(summary = "更新")
//     @ApiOperationSupport(order = 7)
//     public void update() {
//         // 修改数据
//         UpdateRequest updateRequest = new UpdateRequest().index(INDEX_NAME).id("1003");
//         updateRequest.doc(XContentType.JSON, "sex", "女");
//         UpdateResponse updateResponse;
//         try {
//             updateResponse = CLIENT.update(updateRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         DocWriteResponse.Result updateResponseResult = updateResponse.getResult();// 修改响应状态（CREATED:修改成功）
//         log.info("updateResponseResult:{}", updateResponseResult);
//     }
//
//     @GetMapping("/get")
//     @Operation(summary = "查询索引")
//     @ApiOperationSupport(order = 8)
//     public void get() {
//         GetIndexResponse getIndexResponse;
//         try {
//             getIndexResponse = CLIENT.indices().get(new GetIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         Map<String, List<AliasMetadata>> aliases = getIndexResponse.getAliases();// 别名
//         Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();// 配置 机构
//         Map<String, Settings> settings = getIndexResponse.getSettings();
//         log.info("aliases:{}", aliases);
//         log.info("mappings:{}", mappings);
//         log.info("settings:{}", settings);
//         Map<String, String> dataStreams = getIndexResponse.getDataStreams();
//         log.info("dataStreams:{}", dataStreams);
//     }
//
//     @GetMapping("/get/id")
//     @Operation(summary = "根据id查询")
//     @ApiOperationSupport(order = 8)
//     public void getById() {
//         GetResponse getResponse;
//         try {
//             getResponse = CLIENT.get(new GetRequest().index(INDEX_NAME).id("16"), RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         String sourceAsString = getResponse.getSourceAsString();
//         log.info("sourceAsString:{}", sourceAsString);
//     }
//
//     @GetMapping("/match/all/query")
//     @Operation(summary = "查询索引中全部的数据")
//     @ApiOperationSupport(order = 9)
//     public void matchAllQuery() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
//         SearchResponse searchResponse;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         TotalHits totalHits = hits.getTotalHits();
//         log.info("totalHits:{}", totalHits);
//         TimeValue took = searchResponse.getTook();
//         log.info("took:{}", took);
//         SearchHit[] searchHits = hits.getHits();
//         log.info("searchHits:{}", Arrays.toString(searchHits));
//     }
//
//     @GetMapping("/term/query")
//     @Operation(summary = "条件查询")
//     @ApiOperationSupport(order = 10)
//     public void termQuery() {
//         // 条件查询 termQuery
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         // 字符串查询(条件分词)
//         // QueryBuilders.queryStringQuery("搜索")默认匹配所有域
//         // 如果如果添加.field("name")：表示只在name字段进行搜索
//         QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("宋雪").field("name");
//         // searchRequest.source(new SearchSourceBuilder().query(queryStringQueryBuilder));
//
//         // 字符串查询(条件不分词)
//         TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "宋雪");
//         // 搜不到结果：为什么？没有使用分词器, 默认的分词器会单独分成每个字查询
//         searchRequest.source(new SearchSourceBuilder().query(termQueryBuilder));
//         SearchResponse searchResponse;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/wildcard/query")
//     @Operation(summary = "模糊查询（通配符查询）")
//     @ApiOperationSupport(order = 11)
//     public void wildcardQuery() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         // *：表示所有的任意的多个字符组成
//         // ?：表示1个任意的字符
//         WildcardQueryBuilder wildcarded = QueryBuilders.wildcardQuery("name", "宋*");
//
//         searchRequest.source(new SearchSourceBuilder().query(wildcarded));
//         SearchResponse searchResponse;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/paged/query")
//     @Operation(summary = "分页 排序 过滤字段 查询")
//     @ApiOperationSupport(order = 11)
//     public void pagedQuery() {
//         // 分页 排序 过滤字段 查询
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//         //  （当前页码-1）*每页显示条数
//         builder.from(1);// 分页用
//         builder.size(100);// 分页用
//         // builder.sort("age", SortOrder.DESC);// 排序用
//         String[] excludes = {};// 排除的内容
//         String[] includes = {"name", "age"};// 包含的内容
//         builder.fetchSource(includes, excludes);// 过滤字段查询
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest.source(builder), RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/match/query")
//     @Operation(summary = "组合查询")
//     @ApiOperationSupport(order = 12)
//     public void matchQuery() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//         boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));// 满足这个条件
//         boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));// 不满足这个条件
//         // --------------------------满足30或者满足40------------------------------------
//         boolQueryBuilder.should(QueryBuilders.matchQuery("age", 30));
//         boolQueryBuilder.should(QueryBuilders.matchQuery("age", 40));
//         //  （当前页码-1）*每页显示条数
//         builder.from(1);// 分页用
//         builder.size(100);// 分页用
//         searchRequest.source(builder.query(boolQueryBuilder));
//         // -----------------------------------------------------------------------------
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/range/query")
//     @Operation(summary = "范围查询")
//     @ApiOperationSupport(order = 13)
//     public void rangeQuery() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         // 范围查询
//         RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
//         rangeQuery.gte(30);//>=
//         rangeQuery.lte(40);//<=
//         searchRequest.source(builder.query(rangeQuery));
//
//         // ---------------------------------------------------------------------------
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/fuzzy/query")
//     @Operation(summary = "模糊查询")
//     @ApiOperationSupport(order = 14)
//     public void fuzzyQuery() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         // -----------------------------------------------------------------------------
//         // 模糊查询
//         searchRequest.source(builder.query(QueryBuilders.fuzzyQuery("name", "冉").fuzziness(Fuzziness.ONE)));
//         // -----------------------------------------------------------------------------
//         // ---------------------------------------------------------------------------
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/highlight/builder/query")
//     @Operation(summary = "高亮查询")
//     @ApiOperationSupport(order = 15)
//     public void highlightBuilderQuery() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         // 高亮查询
//         TermsQueryBuilder termQueryBuilder = QueryBuilders.termsQuery("name", "张");
//         HighlightBuilder highlightBuilder = new HighlightBuilder();
//         highlightBuilder.preTags("<font color='red' >");// 添加前缀
//         highlightBuilder.postTags("</font>");// 添加后缀
//         highlightBuilder.field("name");// 高亮标签内容
//         builder.highlighter(highlightBuilder);
//         builder.query(termQueryBuilder);
//         searchRequest.source(builder);
//         // ---------------------------------------------------------------------------
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/aggregation/builder")
//     @Operation(summary = "聚合查询")
//     @ApiOperationSupport(order = 16)
//     public void aggregationBuilder() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         // -----------------------------------------------------------------------------
//         // 聚合查询
//         AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("name");
//         builder.aggregation(aggregationBuilder);
//         searchRequest.source(builder);
//         // ---------------------------------------------------------------------------
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     @GetMapping("/group/aggregation/builder")
//     @Operation(summary = "分组查询")
//     @ApiOperationSupport(order = 17)
//     public void groupAggregationBuilder() {
//         SearchRequest searchRequest = new SearchRequest().indices(INDEX_NAME);
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         // ----------------------------------------------------------------------------
//         // 分组查询
//         AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
//         builder.aggregation(aggregationBuilder);
//         searchRequest.source(builder);
//         // ---------------------------------------------------------------------------
//         SearchResponse searchResponse = null;
//         try {
//             searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
//         SearchHits hits = searchResponse.getHits();
//         log.info("总记录数：" + hits.getTotalHits());
//         for (SearchHit hit : hits.getHits()) {
//             log.info("json: " + hit.getSourceAsString());
//             log.info("获取name:" + hit.getSourceAsMap().get("name"));
//             log.info("-------------------------------------------------");
//         }
//     }
//
//     private static RestHighLevelClient getClient() {
//         return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
//     }
// }
