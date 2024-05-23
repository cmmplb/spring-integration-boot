package com.cmmplb.elk.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2020/09/10 下午 08:54
 */

@Slf4j
@Component
public class SchedulerTest {

    @Scheduled(cron = "*/50 * * * * ?")
    public void schedule() {
        log.info("SchedulerTest run...");
    }
}
