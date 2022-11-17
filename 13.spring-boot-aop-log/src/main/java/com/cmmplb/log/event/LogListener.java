
package com.cmmplb.log.event;

import com.cmmplb.core.threads.ThreadContext;
import com.cmmplb.log.dao.LogDao;
import com.cmmplb.log.entity.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogListener {

    @Autowired
    private LogDao logDao;

    @EventListener(LogEvent.class)
    public void saveSysLog(LogEvent event) {
        // 自定义异步线程添加
        ThreadContext.executeTask(() -> {
            // 保存系统日志
            logDao.saveLog((Log) event.getSource());
        });
    }
}
