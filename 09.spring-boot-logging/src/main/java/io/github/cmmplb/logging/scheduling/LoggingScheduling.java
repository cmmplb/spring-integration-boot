package io.github.cmmplb.logging.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2025-05-09 16:46:08
 * @since jdk 1.8
 */

@Slf4j
@Component
public class LoggingScheduling {

    private static final Logger userLoginLog = LoggerFactory.getLogger("USER_LOGIN");

    @Scheduled(cron = "0/3 * * * * ?")
    public void println() {
        log.info("{}", System.currentTimeMillis());
        log.debug("{}", System.currentTimeMillis());
        log.warn("{}", System.currentTimeMillis());
        log.error("{}", System.currentTimeMillis());
        log.trace("{}", System.currentTimeMillis());
        userLoginLog.info("{}", System.currentTimeMillis());
        int i = 1/0;
    }
}
