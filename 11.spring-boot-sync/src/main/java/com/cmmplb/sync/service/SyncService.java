package com.cmmplb.sync.service;

import java.util.concurrent.Future;

/**
 * @author penglibo
 * @date 2021-09-13 15:12:30
 * @since jdk 1.8
 */
public interface SyncService {

    /**
     * 同步方法
     */
    void sync();

    /**
     * 异步方法
     */
    void async();

    /**
     * 异步方法-带返回值
     */
    Future<String> asyncReturn();
}
