package io.github.cmmplb.start.utils;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * @author penglibo
 * @date 2025-04-11 11:25:50
 * @since jdk 1.8
 */
public class LogbackUtil extends PropertyDefinerBase {

    public static final String ENV = "local";
    public static final String LOG_DIR = "/Users/penglibo/Cmmplb/Logs/1";
    public static final String LOG_DIR_PATH = "/Users/penglibo/Cmmplb/Logs/2";
    public static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n";
    public static final String LOG_FILE = "app.log";

    // <property name="logPattern" value="${io.github.cmmplb.start.utils.LogbackUtil.LOG_PATTERN}"/>
    @Override
    public String getPropertyValue() {
        return ENV;
    }

    public static String getEnv(){
        return ENV;
    }
}
