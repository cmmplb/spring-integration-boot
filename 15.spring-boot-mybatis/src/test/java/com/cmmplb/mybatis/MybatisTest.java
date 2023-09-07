package com.cmmplb.mybatis;

import com.cmmplb.mybatis.dao.UserMapper;
import com.cmmplb.mybatis.entity.User;
import com.cmmplb.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    // 使用 Mockito 模拟
    @MockBean
    private UserMapper userDao;

    @Test
    public void contextLoads() {
        // 测试当前对象id是否注入
        // System.out.println(userService.testCurrentUserId());

        // 定义当调用mock userDao的getUserById()方法，并且参数为3时，就返回id为200、name为I'm mock3的user对象
        User user1 = new User();
        user1.setId((long) 200);
        user1.setName("I'm mock3");
        Mockito.when(userDao.selectById((long) 3)).thenReturn(user1);

        // 当使用任何整数值调用 userService 的 getUserById() 方法时，就回传一个名字为 I'm mock3 的 user 对象
        Mockito.when(userService.getById(Mockito.anyLong())).thenReturn(user1);

        // 当调用 userService 的 save() 方法时，不管传进来的 user 是什麽，都回传 100
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(false);

        // 当调用 userService 的 getById() 时的参数是 9 时，抛出一个 RuntimeException
        Mockito.when(userService.getById((long) 9)).thenThrow(new RuntimeException("mock throw exception"));

        // 调用list方法时抛出异常 RuntimeException
        Mockito.doThrow(new RuntimeException("mock throw exception")).when(userService).list();

        // 检查调用 userService 的 getById()、且参数为3的次数是否为1次
        Mockito.verify(userService, Mockito.times(1)).getById(Mockito.eq((long) 3));

        // 使用 Mockito 在 mock 对象时，有一些限制需要遵守
        // 不能 mock 静态方法
        // 不能 mock private 方法
        // 不能 mock final class

        // 返回的会是名字为I'm mock 3的user对象
        User user2 = userService.getById((long) 4);
        log.info("user2:{}", user2);
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
