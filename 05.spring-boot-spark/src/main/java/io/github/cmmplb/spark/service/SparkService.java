package io.github.cmmplb.spark.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.github.cmmplb.spark.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author penglibo
 * @date 2025-06-03 09:30:34
 * @since jdk 1.8
 */

@Slf4j
@Service
public class SparkService {

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private JavaSparkContext sparkContext;

    // 单词计数示例
    public long countWords(String text) {
        JavaRDD<String> words = sparkContext.parallelize(Arrays.asList(text.split(" ")));
        return words.count();
    }

    // 读取CSV文件示例
    public Dataset<Row> readCsv(String filePath) {
        try {
            return sparkSession.read()
                    .option("header", "true")
                    .option("inferSchema", "true")
                    .csv(filePath);
        } catch (Exception e) {
            throw new RuntimeException("读取CSV文件失败", e);
        }
    }

    // SQL查询示例
    public Dataset<Row> executeSql(String sql) {
        Dataset<Row> rowDataset = readTable();
        rowDataset.show();
        try {
            return sparkSession.sql(sql);
        } catch (Exception e) {
            throw new RuntimeException("SQL执行失败", e);
        }
    }

    public Dataset<Row> readTable() {
        Map<String, String> options = new HashMap<>();
        options.put("url", "jdbc:mysql://localhost:3306/spring_boot_spark?useSSL=false&serverTimezone=UTC");
        options.put("dbtable", "user");
        options.put("user", "root");
        options.put("password", "cmmplb");
        options.put("driver", "com.mysql.cj.jdbc.Driver");
        return sparkSession.read().format("jdbc").options(options).load();
    }

    // MLlib 示例：简单统计
    public Double calculateAverage(List<Integer> numbers) {
        try {
            JavaRDD<Integer> rdd = sparkContext.parallelize(numbers);
            return rdd.mapToDouble(Integer::doubleValue).mean();
        } catch (Exception e) {
            throw new RuntimeException("计算平均值失败", e);
        }
    }

    public Dataset<Row> predict(PipelineModel model, String text) {
        // 创建测试数据
        Dataset<Row> testData = sparkSession.createDataFrame(Arrays.asList(
                RowFactory.create(text)
        ), new StructType(new StructField[]{
                new StructField("text", DataTypes.StringType, false, Metadata.empty())
        }));

        // 预测
        return model.transform(testData);
    }

    public PipelineModel trainSentimentAnalysisModel() {
        // 创建训练数据
        List<Row> data = Arrays.asList(
                RowFactory.create(0.0, "I like Spark"),
                RowFactory.create(1.0, "I don't like Spark")
        );

        // 定义Schema
        StructType schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("text", DataTypes.StringType, false, Metadata.empty())
        });

        // 创建DataFrame
        Dataset<Row> training = sparkSession.createDataFrame(data, schema);

        // 构建机器学习管道
        Tokenizer tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words");
        HashingTF hashingTF = new HashingTF().setInputCol(tokenizer.getOutputCol())
                .setOutputCol("features").setNumFeatures(1000);
        LogisticRegression lr = new LogisticRegression().setMaxIter(10).setRegParam(0.01);

        // 组装管道
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{tokenizer, hashingTF, lr});

        // 训练模型
        return pipeline.fit(training);
    }

    public Dataset<Row> createDataFrame() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> rdd = sparkContext.parallelize(data);
        return sparkSession.createDataFrame(rdd, Integer.class);
    }

    public void stream(String host, int port) {
        // 创建Streaming上下文，批处理间隔为5秒
        SparkConf conf = new SparkConf().setAppName("StreamingExample").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));

        // 创建套接字输入流
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream(host, port);

        // 简单处理：统计单词数
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")).iterator());
        words.count().print();


        // 启动流处理
        jssc.start();
    }

    public void calculateStats(String jsonData) {
        log.info("jsonData: {}", jsonData);
        List<User> userList = JSON.parseObject(jsonData, new TypeReference<List<User>>() {
        });
        log.info("userList: {}", userList);
        // 将Java对象转换为Spark DataFrame进行分布式处理
        Dataset<Row> df = sparkSession.createDataset(userList, Encoders.bean(User.class)).toDF();

        // 展开tagList数组为单独的标签行
        Dataset<Row> explodedDF = df.select(
                // 展开后列名为"tag"
                functions.explode(functions.col("tagList")).as("tag")
        );
        Row aggRow = explodedDF.agg(
                functions.sum("tag.number").as("totalNumber")
        ).first();
        Long totalNumber = aggRow.<Long>getAs("totalNumber");
        log.info("totalNumber: {}", totalNumber);

        Dataset<Row> userTagListDataset = df.select(functions.explode(functions.col("tagList")).as("tag"))
                .withColumn("category", functions.when(functions.col("tag.category").isNull()
                                .or(functions.trim(functions.col("tag.category")).equalTo("")), "无")
                        .otherwise(functions.col("tag.category"))
                )
                // 展开结构体字段为独立列
                .withColumn("id", functions.col("tag.id").cast(DataTypes.LongType))
                .withColumn("name", functions.when(functions.col("tag.name").isNull()
                        .or(functions.trim(functions.col("tag.name")).equalTo("")), "无")
                )
                .withColumn("number", functions.when(functions.col("tag.number").isNull(), 0)
                )
                .groupBy("category")
                .agg(
                        // 统计每个标签的数量
                        functions.sum("number").as("tagNumber"),
                        // 收集所有name到数组中
                        functions.collect_list("name").as("tagNames")
                )
                .orderBy(functions.desc("tagNumber"));

        // 查看列结构
        userTagListDataset.printSchema();

        Function<Row, String> safeProvince = row -> {
            String category = row.getString(0);
            return (category == null || category.trim().isEmpty()) ? "无" : category;
        };

        List<User.Tag> userTagList = userTagListDataset.collectAsList().stream()
                .map(row -> new User.Tag(row.getLong(1),
                        row.getString(0),
                        safeProvince.apply(row),
                        (int) row.getLong(1)))
                .collect(Collectors.toList());
        log.info("userTagList: {}", userTagList);
    }
}
