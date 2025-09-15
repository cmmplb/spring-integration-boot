package io.github.cmmplb.spark.test;

import io.github.cmmplb.spark.service.SparkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class SparkTest {

    @Autowired
    private SparkService sparkService;

    @Test
    public void testWordCount() {
        String text = "Hello world, this is a test";
        long count = sparkService.countWords(text);
        System.out.println("单词数量: " + count);
    }

    @Test
    public void testCsv() {
        String filePath = "05.spring-boot-spark/src/main/resources/csv/sample.csv";
        Dataset<Row> df = sparkService.readCsv(filePath);
        df.show();
    }

    @Test
    public void testSql() {
        String sql = "SELECT * FROM src/test/resources/sample.csv";
        Dataset<Row> result = sparkService.executeSql(sql);
        result.show();
    }

    @Test
    public void testMllib() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        double average = sparkService.calculateAverage(numbers);
        System.out.println("平均值: " + average);
    }

    @Test
    public void testDataFrame() {
        Dataset<Row> df = sparkService.createDataFrame();
        df.show();
    }

    @Test
    public void testStream() {
        String host = "localhost";
        int port = 9999;
        sparkService.stream(host, port);
    }

    @Test
    public void testCalculateStats() {
        String jsonData = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"张三\",\n" +
                "        \"age\": 20,\n" +
                "        \"tagList\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"普通用户\",\n" +
                "                \"category\": \"用户等级\",\n" +
                "                \"number\": 10\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"张三\",\n" +
                "        \"age\": 20,\n" +
                "        \"tagList\": [\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"VIP\",\n" +
                "                \"category\": \"用户等级\",\n" +
                "                \"number\": 20\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
        sparkService.calculateStats(jsonData);
    }
}



