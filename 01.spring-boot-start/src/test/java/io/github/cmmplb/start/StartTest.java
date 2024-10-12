package io.github.cmmplb.start;

import io.github.cmmplb.start.entity.User;
import io.github.cmmplb.start.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 * Spring上下文环境测试
 * 注意：
 * 如果引用的包是 org.junit.jupiter.api.Test 和 org.junit.jupiter.api.BeforeEach，是 jupiter.api 包下面的，此时测试类只用了 @SpringBootTest 这一个注解；
 * 如果引用的是 org.junit.Test 和 org.junit.Before，测试类上面必须同时使用 @RunWith(SpringRunner.class) 和 @SpringBootTest(classes = MySpringbootApplication.class)。
 */

@SpringBootTest(classes = StartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 开启虚拟MVC调用
@AutoConfigureMockMvc
public class StartTest {

    @Autowired
    private UserService userService;

    // 常用注解：
    // - @BeforeEach：在每个单元测试方法执行前都执行一遍
    // - @BeforeAll：在每个单元测试方法执行前执行一遍（只执行一次）
    // - @DisplayName("商品入库测试")：用于指定单元测试的名称
    // - @Disabled：当前单元测试置为无效, 即单元测试时跳过该测试
    // - @RepeatedTest(n)：重复性测试, 即执行n次
    // - @ParameterizedTest：参数化测试,
    // - @ValueSource(ints = {1, 2, 3})：参数化测试提供数据

    @BeforeEach
    @DisplayName("初始化方法")
    public void init() {
        System.out.println("初始化方法");
    }

    @Test
    @DisplayName("根据id获取用户信息")
    public void getUser() {
        User user = userService.selectById(1);
        System.out.println("测试结果：" + user);
    }

    @Autowired MockMvc mvc;

    @Test
    @DisplayName("开启web环境，发送虚拟测试请求，匹配响应执行状态")
    public void doPost() throws Exception {
        // 开启web环境，需要添加@AutoConfigureMockMvc注解
        // http://localhost:80/
        // 创建虚拟请求，当前访问/
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/");
        // 执行对应的请求
        ResultActions actions = mvc.perform(builder);

        // ===匹配响应执行状态===
        // 设定预期值 与真实值进行比较，成功测试通过，失败测试失败
        // 定义执行结果匹配器
        StatusResultMatchers status = MockMvcResultMatchers.status();
        // 定义执行状态。(预计本次调用是成功的：状态200)
        ResultMatcher ok = status.isOk();
        // 使用本次真实执行结果与预期结果进行比对
        actions.andExpect(ok);
    }

    @Test
    @DisplayName("开启web环境，发送虚拟测试请求，匹配响应体(字符串)")
    public void doPostString() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/start");
        // 执行对应的请求
        ResultActions actions = mvc.perform(builder);
        // ===匹配响应体(字符串)===
        // 定义执行结果匹配器
        ContentResultMatchers content = MockMvcResultMatchers.content();
        // 定义预期执行结果
        ResultMatcher result = content.string("start");
        // 添加预计值到本次调用过程中进行匹配
        actions.andExpect(result);
    }

    @Test
    @DisplayName("开启web环境，发送虚拟测试请求，匹配响应体(json)")
    public void doPostJson() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/");
        // 执行对应的请求
        ResultActions actions = mvc.perform(builder);
        // ===匹配响应体(字符串)===
        // 定义执行结果匹配器
        ContentResultMatchers content = MockMvcResultMatchers.content();
        // 定义预期执行结果
        ResultMatcher result = content.json("{\n" +
                "  \"code\": 200,\n" +
                "  \"msg\": \"操作成功\",\n" +
                "  \"data\": {\n" +
                "    \"age\": \"20\",\n" +
                "    \"startName2\": \"李四\"\n" +
                "  },\n" +
                "  \"timestamp\": 1728716164301\n" +
                "}");
        // 添加预计值到本次调用过程中进行匹配
        actions.andExpect(result);
    }

    @Test
    @DisplayName("开启web环境，发送虚拟测试请求，匹配响应头")
    public void doPostHeader() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/start");
        // 执行对应的请求
        ResultActions actions = mvc.perform(builder);
        // ===匹配响应体(字符串)===
        // 定义执行结果匹配器
        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher contentType = header.string("Content-Type", "application/json");
        // 添加预计值到本次调用过程中进行匹配
        actions.andExpect(contentType);
    }

    @Test
    @DisplayName("属性注入")
    public void setFieldTest() {
        System.out.println(userService.getName());
        // ReflectionTestUtils.setField方法来动态地设置value字段的值
        ReflectionTestUtils.setField(userService, "name", "王二");
        System.out.println(userService.getName());
    }

    private static int count = 0;

    @RepeatedTest(3)
    @DisplayName("重复性测试")
    public void testRepeatedTest() {
        count++;
        System.out.println("重复性测试:" + count);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    // @ValueSource(strings = {"张三", "李四"})
    @DisplayName("参数化测试")
    public void testParameterizedTest(Integer id) {
        User user = userService.selectById(id);
        System.out.println("参数化测试:" + user);
    }

    @Test
    @DisplayName("测试断言equals")
    void testEquals() {
        assertTrue(3 < 4);
        assertTrue(4 < 3);
    }

    @Nested
    @DisplayName("嵌套测试")
    class NestedTestClas2 {

        @Test
        @DisplayName("取消订单1")
        void cancelOrder1() {
            System.out.println("取消订单1");
        }

        @Test
        @DisplayName("取消订单2")
        void cancelOrder2() {
            System.out.println("取消订单2");
        }
    }

    @Test
    @DisplayName("测试组合断言")
    void testAll() {
        assertAll("测试商品下单",
                () -> {
                    int num = 1;
                    num--;
                    // 模拟用户余额扣减
                    assertTrue(num > 0, "余额不足");
                },
                () -> {
                    int num = 1;
                    num--;
                    // 模拟扣减库存
                    assertTrue(num > 0, "库存不足");
                },
                () -> {
                    // 模拟交易流水落库
                    assertNotNull(new Object());
                }
        );
    }
}



