package com.cmmplb.sync;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureDemo {

    /**
     * CPU核数
     */
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            //核心线程数
            AVAILABLE_PROCESSORS,
            //最大线程数
            3 * AVAILABLE_PROCESSORS,
            //keepAliveTime
            3,
            TimeUnit.SECONDS,
            //阻塞队列
            new LinkedBlockingDeque<>(20),
            r -> new Thread(r, "sync-thread-" + r.hashCode()));


    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("demo start....." + startTime);
        demo3();
        System.out.println("demo end.....costTime = " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 基于allOf, 并行处理多个任务, 等待所有任务执行完毕后返回
     */
    public static void demo3(){
        //用于整体接收各个任务的返回值
        Map<String, Object> dataMap = new ConcurrentHashMap<>();

        List<CompletableFuture<String>> futureList = new ArrayList<>();
        futureList.add(doSomethingA("A", dataMap));
        futureList.add(doSomethingB("B", dataMap));
        futureList.add(doSomethingC("C", dataMap));

        CompletableFuture<Void> result = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

        try {
            result.get(6, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("result = " + dataMap);
        //结果为：{doSomeThingB=B, doSomeThingA=A}
    }

    /**
     * 基于thenCompose, 第一个任务执行完后, 第二个任务使用第一个任务的返回作为参数
     */
    public static void demo1() throws Exception {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();

        CompletableFuture<String> completableFuture = doSomethingA("A", dataMap)
                .thenCompose(id -> doSomethingB(id, dataMap));

        String result = completableFuture.get(2, TimeUnit.SECONDS);
        System.out.println("result = " + result);
        //结果为：A is done is done
    }

    /**
     * 基于thenCombine, 当两个任务都完成后, 使用两者的结果作为参数再执行一个异步任务
     */
    public static void demo2() throws Exception {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();

        CompletableFuture<String> completableFuture = doSomethingA("A", dataMap)
                .thenCombine(doSomethingB("B", dataMap), (a, b) -> a + " - " + b);

        String result = completableFuture.get(2, TimeUnit.SECONDS);
        System.out.println("result = " + result);
        //结果为：A is done - B is done
    }


    /**
     * 使用CompletionService批量异步处理
     *
     * CompletionService 能够让异步任务的执行结果有序化, 先执行完的先进入阻塞队列,
     * 利用这个特性, 你可以轻松实现后续处理的有序性, 避免无谓的等待
     */
    public static void demo4() {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();

        CompletionService<Map<String, Object>> cs = new ExecutorCompletionService<>(THREAD_POOL_EXECUTOR);
        cs.submit(() -> doSomething1("doSomething1", dataMap));
        cs.submit(() -> doSomething2("doSomething2", dataMap));
        cs.submit(() -> doSomething3("doSomething3", dataMap));

        for (int i = 0; i < 3; i++) {
            Map<String, Object> resultMap = null;
            try {
                //依次从队列中取任务执行结果
                resultMap = cs.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(resultMap);
        }
    }



    /**
     * @param dataMap 用于整体接收方法的返回值
     */
    public static CompletableFuture<String> doSomethingA(String taskId, Map<String, Object> dataMap) {
        System.out.println("doSomethingA start....." + System.currentTimeMillis());

        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dataMap.put("doSomeThingA", "A");
                return taskId + " is done";
            }
        }, THREAD_POOL_EXECUTOR);
    }

    public static CompletableFuture<String> doSomethingB(String taskId, Map<String, Object> dataMap) {
        System.out.println("doSomethingB start....." + System.currentTimeMillis());

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dataMap.put("doSomeThingB", "B");
            return taskId + " is done";

        }, THREAD_POOL_EXECUTOR);
    }

    public static CompletableFuture<String> doSomethingC(String taskId, Map<String, Object> dataMap) {
        System.out.println("doSomethingC start....." + System.currentTimeMillis());

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dataMap.put("doSomeThingC", "C");
            return taskId + " is done";

        }, THREAD_POOL_EXECUTOR);
    }


    public static Map<String, Object> doSomething1(String taskId, Map<String, Object> dataMap) {
        System.out.println("doSomething1 start....." + System.currentTimeMillis());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataMap.put(taskId, "doSomething1");
        return dataMap;
    }

    public static Map<String, Object> doSomething2(String taskId, Map<String, Object> dataMap) {
        System.out.println("doSomething2 start....." + System.currentTimeMillis());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataMap.put(taskId, "doSomething2");
        return dataMap;
    }

    public static Map<String, Object> doSomething3(String taskId, Map<String, Object> dataMap) {
        System.out.println("doSomething3 start....." + System.currentTimeMillis());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataMap.put(taskId, "doSomething3");
        return dataMap;
    }
}
