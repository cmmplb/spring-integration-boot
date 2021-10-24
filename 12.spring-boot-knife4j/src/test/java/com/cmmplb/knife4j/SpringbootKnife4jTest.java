package com.cmmplb.knife4j;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class SpringbootKnife4jTest {

    /**
     * 常用注解#
     * @BeforeEach：在每个单元测试方法执行前都执行一遍
     * @BeforeAll：在每个单元测试方法执行前执行一遍（只执行一次）
     * @DisplayName("商品入库测试")：用于指定单元测试的名称
     * @Disabled：当前单元测试置为无效，即单元测试时跳过该测试
     * @RepeatedTest(n)：重复性测试，即执行n次
     * @ParameterizedTest：参数化测试，
     * @ValueSource(ints = {1, 2, 3})：参数化测试提供数据
     */

    @Test
    public void contextLoads() {
        System.out.println("test");
    }


    @Test
    @DisplayName("测试断言equals")
    void testEquals() {
        assertTrue(3 < 4);
    }

    //....
    @Nested
    @DisplayName("内嵌订单测试")
    class OrderTestClas {

        @Test
        @DisplayName("取消订单")
        void cancelOrder() {
            int status = -1;
            System.out.println("取消订单成功,订单状态为:" + status);
        }
    }

    @Test
    @DisplayName("测试组合断言")
    void testAll() {
        assertAll("测试item商品下单",
                () -> {
                    //模拟用户余额扣减
                    assertTrue(1 < 2, "余额不足");
                },
                () -> {
                    //模拟item数据库扣减库存
                    assertTrue(3 < 4);
                },
                () -> {
                    //模拟交易流水落库
                    assertNotNull(new Object());
                }
        );
    }

}
