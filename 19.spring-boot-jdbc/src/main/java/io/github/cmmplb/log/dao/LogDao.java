package io.github.cmmplb.log.dao;

import io.github.cmmplb.log.entity.Log;

/**
 * @author penglibo
 * @date 2021-04-14 14:46:08
 */

public interface LogDao {

    /**
     * 保存日志
     * @param log
     */
    void saveLog(Log log);
}
