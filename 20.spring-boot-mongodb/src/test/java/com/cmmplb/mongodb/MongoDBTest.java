package com.cmmplb.mongodb;

import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class MongoDBTest {

    @Test
    public void contextLoads() {
    }

    /*public void test1() {
        //创建连接
        MongoClient client = new MongoClient("193.112.252.232");

        //打开数据库
        MongoDatabase commentdb = client.getDatabase("commentdb");
        //获取集合
        MongoCollection<Document> comment = commentdb.getCollection("comment");

        //查询
        FindIterable<Document> documents = comment.find();

        //查询记录获取文档集合
        for (Document document : documents) {
            System.out.println("_id：" + document.get("_id"));
            System.out.println("内容：" + document.get("content"));
            System.out.println("用户ID:" + document.get("userid"));
            System.out.println("点赞数：" + document.get("thumbup"));
        }
        //关闭连接
        client.close();
    }*/

    @Test
    public void test() {
        //        创建MongoDB的链接客户端,用户名为：root，密码为：cmmplb，数据库为：admin,集合为：spit
        MongoClient mongoClient = MongoClients.create("mongodb://root:cmmplb@193.112.252.232:27017/?authSource=admin");
//        获取对应的数据库
        MongoDatabase admin = mongoClient.getDatabase("admin");
//        获取对应的文档集合
        MongoCollection<org.bson.Document> spit = admin.getCollection("spit");
//        查询该集合中的所有文档
        FindIterable<org.bson.Document> documents = spit.find();
//        遍历文档数据，打印出nickname的值
        for (org.bson.Document document : documents) {
            System.out.println(document.getString("nickname"));

        }

    }
}
