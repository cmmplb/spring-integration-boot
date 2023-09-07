package com.cmmplb.start;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class StartTest {

    @Data
    @AllArgsConstructor
    public static class Student {

        private Long id;

        private String name;
    }

    public static void main(String[] args) {
        System.out.println("1");
        //
        System.out.println("Hello");
    }

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
                    // 模拟用户余额扣减
                    assertTrue(1 < 2, "余额不足");
                },
                () -> {
                    // 模拟item数据库扣减库存
                    assertTrue(3 < 4);
                },
                () -> {
                    // 模拟交易流水落库
                    assertNotNull(new Object());
                }
        );
    }

    @Test
    public void test() {
        String json = "[{\\\"controlGe\\\":\\\"1\\\",\\\"controlLe\\\":\\\"1\\\",\\\"controlPatternId\\\":\\\"次数\\\",\\\"rebateTypeCode\\\":\\\"RT02\\\",\\\"promotionId\\\":\\\"M-0000001650\\\",\\\"strategyId\\\":\\\"ZC1\\\",\\\"strategyTypeId\\\":\\\"ZC13\\\",\\\"tpStrategyId\\\":\\\"RT02\\\",\\\"tMonth\\\":\\\"12\\\",\\\"month\\\":\\\"12\\\"},{\\\"controlGe\\\":\\\"-2147483648\\\",\\\"controlLe\\\":\\\"30\\\",\\\"controlPatternId\\\":\\\"天数\\\",\\\"rebateTypeCode\\\":\\\"RT03\\\",\\\"promotionId\\\":\\\"M-0000001734\\\",\\\"strategyId\\\":\\\"ZC1\\\",\\\"strategyTypeId\\\":\\\"ZC11\\\",\\\"tpStrategyId\\\":\\\"RT03\\\",\\\"tMonth\\\":\\\"05\\\",\\\"month\\\":\\\"05\\\"},{\\\"controlGe\\\":\\\"1\\\",\\\"controlLe\\\":\\\"1\\\",\\\"controlPatternId\\\":\\\"次数\\\",\\\"rebateTypeCod\n" +
                "2022-03-11 16:17:11.710 10298-10344/com.cestbon.marketing.assistant.dev I/console: [LOG][___Connect_-2055230675___]e\\\":\\\"RT01\\\",\\\"promotionId\\\":\\\"M-0000001735\\\",\\\"strategyId\\\":\\\"ZC1\\\",\\\"strategyTypeId\\\":\\\"ZC13\\\",\\\"tpStrategyId\\\":\\\"RT01\\\",\\\"tMonth\\\":\\\"03\\\",\\\"month\\\":\\\"03\\\"},{\\\"controlGe\\\":\\\"-2147483648\\\",\\\"controlLe\\\":\\\"30\\\",\\\"controlPatternId\\\":\\\"天数\\\",\\\"rebateTypeCode\\\":\\\"RT01\\\",\\\"promotionId\\\":\\\"M-0000001616\\\",\\\"strategyId\\\":\\\"ZC1\\\",\\\"strategyTypeId\\\":\\\"ZC11\\\",\\\"tpStrategyId\\\":\\\"RT01\\\",\\\"tMonth\\\":\\\"10\\\",\\\"month\\\":\\\"10\\\"},{\\\"controlGe\\\":\\\"-2147483648\\\",\\\"controlLe\\\":\\\"12\\\",\\\"controlPatternId\\\":\\\"月份\\\",\\\"rebateTypeCode\\\":null,\\\"promotionId\\\":\\\"M-0000001738\\\",\\\"strategyId\\\":\\\"ZC2\\\",\\\"strategyTypeId\\\":\\\"ZC25\\\",\\\"tpStrategyId\\\":\\\"RT01\\\",\\\"tMonth\\\":\\\"07\\\",\\\"month\\\":\\\"07\\\"},{\\\"controlGe\\\":\\\"1\\\",\\\"controlLe\\\":\\\"1\\\",\\\"controlPatternId\\\":\\\"次数\\\",\\\"rebateTypeCode\\\":\\\"RT01\\\",\\\"promotionId\\\":\\\"M-0000001604\\\",\\\"strategyId\\\":\\\"ZC1\\\",\\\"strategyTypeId\\\":\\\"ZC13\\\",\\\"tpStrategyId\\\":\\\"RT01\\\",\\\"tMonth\\\":\\\"11\\\",\\\"month\\\":\\\"11\\\"},{\\\"controlGe\\\":\\\"-2147483648\\\",\\\"controlLe\\\":\\\"1\\\",\\\"controlPatternId\\\":\\\"月份\\\",\\\"rebateTypeCod\n" +
                "2022-03-11 16:17:11.710 10298-10344/com.cestbon.marketing.assistant.dev I/console: [LOG][___Connect_-2055230675___]e\\\":null,\\\"promotionId\\\":\\\"M-0000001639\\\",\\\"strategyId\\\":\\\"ZC3\\\",\\\"strategyTypeId\\\":\\\"ZC32\\\",\\\"tpStrategyId\\\":\\\"RT02\\\",\\\"tMonth\\\":\\\"11\\\",\\\"month\\\":\\\"11\\\"},{\\\"controlGe\\\":\\\"1\\\",\\\"controlLe\\\":\\\"2147483647\\\",\\\"controlPatternId\\\":\\\"张数\\\",\\\"rebateTypeCode\\\":null,\\\"promotionId\\\":\\\"M-0000001650\\\",\\\"strategyId\\\":\\\"ZC3\\\",\\\"strategyTypeId\\\":\\\"ZC34\\\",\\\"tpStrategyId\\\":\\\"RT02\\\",\\\"tMonth\\\":\\\"12\\\",\\\"month\\\":\\\"12\\\"},{\\\"controlGe\\\":\\\"1\\\",\\\"controlLe\\\":\\\"2147483647\\\",\\\"controlPatternId\\\":\\\"次数\\\",\\\"rebateTypeCode\\\":null,\\\"promotionId\\\":\\\"M-0000001699\\\",\\\"strategyId\\\":\\\"ZC2\\\",\\\"strategyTypeId\\\":\\\"ZC22\\\",\\\"tpStrategyId\\\":\\\"RT01\\\",\\\"tMonth\\\":\\\"12\\\",\\\"month\\\":\\\"12\\\"},{\\\"controlGe\\\":\\\"-2147483648\\\",\\\"controlLe\\\":\\\"3\\\",\\\"controlPatternId\\\":\\\"月份\\\",\\\"rebateTypeCode\\\":null,\\\"promotionId\\\":\\\"M-0000001604\\\",\\\"strategyId\\\":\\\"ZC2\\\",\\\"strategyTypeId\\\":\\\"ZC26\\\",\\\"tpStrategyId\\\":\\\"RT01\\\",\\\"tMonth\\\":\\\"11\\\",\\\"month\\\":\\\"11\\\"}]";
        json = StringEscapeUtils.unescapeJava(json);
        System.out.println(json);
        List<Map<String, Object>> map = JSONObject.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
        System.out.println(map);
    }

}
