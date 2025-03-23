package io.github.cmmplb.rabbitmq.service;

import io.github.cmmplb.rabbitmq.entity.RequireGoodsList;

/**
 * @author penglibo
 * @date 2025-03-23 13:21:38
 * @since jdk 1.8
 */
public interface RequireGoodsService {

    void test(Long id, int i) throws InterruptedException;

    void rollback(Long id, RequireGoodsList requireGoodsList, Integer currentNum);

    RequireGoodsList getByIdForUpdate(Long id);
}
