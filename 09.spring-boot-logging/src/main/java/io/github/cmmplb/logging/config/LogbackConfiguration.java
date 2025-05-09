package io.github.cmmplb.logging.config;

import io.github.cmmplb.core.utils.LogDirUtil;
import io.github.cmmplb.core.utils.YmlUtil;
import io.github.cmmplb.logging.config.properties.LogbackProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author penglibo
 * @date 2025-04-11 14:39:13
 * @since jdk 1.8
 */

@Slf4j
@Configuration
@EnableConfigurationProperties(LogbackProperties.class)
public class LogbackConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    // @PostConstruct
    public void init() {
        // 初始化设置 logback
        Object loggingFileName = YmlUtil.getApplicationValue("logging.file.name");
        Object applicationName = YmlUtil.getApplicationValue("spring.application.name");
        if (null == applicationName) {
            applicationName = "start";
        }
        // 如果没有设置日志文件名, 则使用路径 + 应用名 + 应用名.log
        if (null == loggingFileName) {
            loggingFileName = LogDirUtil.getLogDir() + File.separator + applicationName + File.separator + applicationName + ".log";
        }
        String logFileName = String.valueOf(loggingFileName);
        // 如果日志文件名不是 ./ 开头并且不是 / 开头, 则使用路径 + 应用名 + 日志文件名
        if (!logFileName.startsWith("." + File.separator) && !String.valueOf(logFileName.charAt(0)).equals(File.separator)) {
            logFileName = LogDirUtil.getLogDir() + File.separator + applicationName + File.separator + logFileName;
        }
        System.setProperty("logging.file.name", logFileName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            // int x = 1/0;
        }catch (Exception e) {
            log.error("LogbackConfiguration setApplicationContext error", e);
        }
        this.applicationContext = applicationContext;
    }

    // public void reassignProperties(String key, String value) {
    //     ConfigurableEnvironment environment = applicationContext.getBean(ConfigurableEnvironment.class);
    //     // 创建一个新的属性源
    //     Map<String, Object> newProperties = new HashMap<>();
    //     newProperties.put(key, value);
    //
    //     // 创建 MapPropertySource 并添加到环境中
    //     MapPropertySource propertySource = new MapPropertySource("customProperties", newProperties);
    //     environment.getPropertySources().addFirst(propertySource);
    //
    //     // 验证属性是否已更新
    //     String applicationName = environment.getProperty(key);
    //     System.out.println("Updated " + key + ":" + applicationName);
    // }

    // @Bean
    // public ConsoleAppender<ILoggingEvent> consoleAppender() {
    //     ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
    //     appender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
    //     PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    //     encoder.setContext(appender.getContext());
    //     encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} - %msg%n");
    //     encoder.start();
    //     appender.setEncoder(encoder);
    //     appender.start();
    //     return appender;
    // }

    // @Bean
    // public FileAppender<ILoggingEvent> fileAppender() {
    //     FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
    //     fileAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
    //     PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    //     encoder.setContext(fileAppender.getContext());
    //     encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} - %msg%n");
    //     encoder.start();
    //     return fileAppender;
    // }
}
