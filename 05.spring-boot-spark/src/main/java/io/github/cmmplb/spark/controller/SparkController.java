package io.github.cmmplb.spark.controller;

import io.github.cmmplb.spark.service.SparkService;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2025-06-03 09:31:07
 * @since jdk 1.8
 */

@RestController
public class SparkController {

    @Autowired
    private SparkService sparkService;

    /**
     * RDD 操作-单词计数
     */
    @GetMapping("/word-count")
    public String wordCount(@RequestParam String text) {
        long count = sparkService.countWords(text);
        return "单词数量: " + count;
    }

    /**
     *
     */
    @GetMapping("/csv")
    public String readCsv(@RequestParam String filePath) {
        Dataset<Row> df = sparkService.readCsv(filePath);
        df.show();
        return "CSV文件已打印到控制台";
    }

    /**
     * SQL 查询-DataFrame 操作
     */
    @GetMapping("/sql")
    public String executeSql(@RequestParam String sql) {
        Dataset<Row> result = sparkService.executeSql(sql);
        result.show();
        return "SQL查询结果已打印到控制台";
    }

    /**
     * 机器学习（MLlib）
     */
    @GetMapping("/mllib")
    public String calculateAverage(@RequestParam List<Integer> numbers) {
        double average = sparkService.calculateAverage(numbers);
        PipelineModel pipelineModel = sparkService.trainSentimentAnalysisModel();
        Dataset<Row> predictions = sparkService.predict(pipelineModel, "This is a great movie!");
        predictions.show();
        return "平均值: " + average;
    }

    @GetMapping("/dataframe")
    public String showDataFrame() {
        Dataset<Row> df = sparkService.createDataFrame();
        df.show();
        return "DataFrame 已打印到控制台";
    }

    /**
     * 实时流处理（Spark Streaming）
     */
    @GetMapping("/stream")
    public String stream(String host, int port) {
        sparkService.stream(host, port);
        return "Spark Streaming";
    }

    @PostMapping("/calculate")
    public void calculateStats(@RequestBody String jsonData) {
        sparkService.calculateStats(jsonData);
    }
}
