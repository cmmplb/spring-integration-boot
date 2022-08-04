package com.cmmplb.mybatis;

import com.cmmplb.mybatis.dao.UserMapper;
import com.cmmplb.mybatis.entity.User;
import com.cmmplb.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class MybatisTest {

    @Autowired
    private UserService userService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        // 测试当前对象id是否注入
        // System.out.println(userService.testCurrentUserId());
    }

    @Test
    public void testOneCache() {
        System.out.println("一级缓存范围: " + sqlSessionFactory.getConfiguration().getLocalCacheScope());
        System.out.println("二级缓存是否被启用: " + sqlSessionFactory.getConfiguration().isCacheEnabled());

        // -----------------------一级缓存生效------------------------------------
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 测试一级缓存-同一个session会话
        for (int i = 0; i < 3; i++) {
            User user = userMapper.selectById(1L);
            log.info("用户：{}", user);
        }

        // -----------------------一级缓存失效------------------------------------
        // 每次Mapper调用selectById方法都会创建一个session，并且在执行完毕后关闭session。所以三次调用并不在一个session中，一级缓存并没有起作用
        for (int i = 0; i < 3; i++) {
            User user = this.userMapper.selectById(1L);
            log.info("用户：{}", user);
            // 如果将该方法放在一个事务当中,一级缓存又生效了。乐观锁重试的Bug就是由于在此场景下使用了一级缓存，查询不到最新的数据库数据导致的
            // 实践中，将Mybatis和Spring进行整合开发，事务控制在service中。如果是执行两次service调用查询相同的用户信息，不走一级缓存，因为Service方法结束，SqlSession就关闭，一级缓存就清空。
        }
    }

    @Test
    public void testTwoCache() {
        System.out.println("一级缓存范围: " + sqlSessionFactory.getConfiguration().getLocalCacheScope());
        System.out.println("二级缓存是否被启用: " + sqlSessionFactory.getConfiguration().isCacheEnabled());

        // -----------------------测试二级缓存------------------------------------

        // 测试二级缓存
        // 二级缓存是针对不同SqlSession直接的缓存，可以理解为mapper级别,这些SqlSession需要是同一个namespace,在xxMapper.xml文件中配置的namespace：

        // 二级缓存需要查询结果映射的pojo对象实现java.io.Serializable接口。如果存在父类、成员pojo都需要实现序列化接口。否则，执行的过程中会直接报错。

        // 如果需要对指定的Mapper使用二级缓存，还需要在对应的xml文件中配置如下内容： <cache/>  -> 见UserMapper.xml

        for (int i = 0; i < 3; i++) {
            User user = this.userService.getById(1L);
            log.info("用户：{}", user);
            // 如果将该方法放在一个事务当中,一级缓存又生效了。乐观锁重试的Bug就是由于在此场景下使用了一级缓存，查询不到最新的数据库数据导致的
            // 实践中，将Mybatis和Spring进行整合开发，事务控制在service中。如果是执行两次service调用查询相同的用户信息，不走一级缓存，因为Service方法结束，SqlSession就关闭，一级缓存就清空。
        }
        // 打印出了命中缓存的概率为：0.5。   -> Cache Hit Ratio [com.cmmplb.mybatis.dao.UserMapper]: 0.5

        // 由于cache是针对整个Mapper中的查询方法的，因此当某个方法不需要缓存时，可在对应的select标签中添加useCache值为false来禁用二级缓存。
    }
}
