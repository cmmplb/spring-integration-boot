package com.cmmplb.mongodb;

import io.github.cmmplb.core.constants.GlobalConstant;
import io.github.cmmplb.core.utils.DateUtil;
import io.github.cmmplb.core.utils.MapObjectUtil;
import io.github.cmmplb.core.utils.UUIDUtil;
import com.cmmplb.mongodb.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

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

    private MongoClient client;

    private MongoCollection<Document> user;

    /**
     * 在Junit5中，@Before和@After注解被@BeforeEach和@AfterEach所替代。
     */
    @BeforeEach
    public void init() {
        // 创建MongoDB的链接客户端,用户名为：root，密码为：cmmplb，数据库为：admin,集合为：spit
        // MongoClient mongoClient = MongoClients.create("mongodb://root:cmmplb@193.112.252.232:27017/?authSource=admin");
        client = MongoClients.create();
        // 获取对应的数据库
        MongoDatabase commentdb = client.getDatabase("cmmplb");
        // 获取对应的文档集合
        user = commentdb.getCollection("user");
    }

    @AfterEach
    public void after() {
        // 关闭连接
        client.close();
    }

    @Test
    public void insert() {
        User user = new User();
        user.set_id(UUIDUtil.uuidTrim());
        user.setName("张三");
        user.setSex(GlobalConstant.NUM_ONE);
        user.setBirthday(new Date());
        user.setDescription("描述");

        Map<String, Object> map = MapObjectUtil.object2Map(user);
        map.put("_id", user.get_id());

        Document doc = new Document(map);
        this.user.insertOne(doc);
    }

    @Test
    public void update() {
        // 修改的条件
        Bson filter = new BasicDBObject("_id", 1L);
        // 修改的数据
        Bson update = new BasicDBObject("$set", new Document("description", "描述1"));
        this.user.updateOne(filter, update);
    }

    @Test
    public void delete() {
        // 删除的条件
        Bson filter = new BasicDBObject("description", "描述");
        this.user.deleteOne(filter);
    }

    @Test
    public void findAll() {
        // 查询该集合中的所有文档
        FindIterable<Document> documents = user.find();
        // 遍历文档数据，打印出nickname的值
        for (Document document : documents) {
            System.out.println("=======================");
            System.out.println(document.getLong(User.COLUMN_ID));
            System.out.println(document.getString(User.COLUMN_NAME));
            System.out.println(GlobalConstant.NUM_ZERO == Byte.parseByte(document.get(User.COLUMN_SEX).toString()) ? "女" : "男");
            System.out.println(DateUtil.formatDate(document.getDate(User.COLUMN_BIRTHDAY), DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
            System.out.println(document.getString(User.COLUMN_DESCRIPTION));
            System.out.println("=======================");
        }
    }
}
