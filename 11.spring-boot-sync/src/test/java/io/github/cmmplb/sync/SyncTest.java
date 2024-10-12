package io.github.cmmplb.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class SyncTest {

    @Test
    public void contextLoads() {
        System.out.println("test");
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        CompletableFuture<Integer> completableFuture1 = getOne(map);

        CompletableFuture<Integer> completableFuture2 = getTwo(map);

        //聚合几个查询的结果返回给前端
        CompletableFuture.allOf(completableFuture1, completableFuture2).join();

        System.out.println(map);
    }

    private static CompletableFuture<Integer> getTwo(Map<String, Object> map) {
        System.out.println("进入A方法：" + System.currentTimeMillis());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put("A", "1");
            System.out.println("A");
            return 1;
        });
    }

    private static CompletableFuture<Integer> getOne(Map<String, Object> map) {
        System.out.println("进入B方法：" + System.currentTimeMillis());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put("B", "2");
            System.out.println("B");
            return 2;
        });
    }


}
