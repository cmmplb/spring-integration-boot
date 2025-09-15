package io.github.cmmplb.logging.utils;

import ch.qos.logback.core.PropertyDefinerBase;
import io.github.cmmplb.core.constants.GlobalConstant;
import io.github.cmmplb.core.utils.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author penglibo
 * @date 2022-08-24 15:36:51
 * @since jdk 1.8
 * 动态设置 logback 日志文件路径
 */

@Slf4j
public class LoggingFilePathPropertyDefiner extends PropertyDefinerBase {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingFilePathPropertyDefiner.class);

    public static String getLoggingFilePath() {
        // 获取环境变量信息
        Byte systemOs = SystemUtils.getSystemOs();
        String logDir = SystemUtils.getDir();
        // 操作系统:0-mac,1-windows,2-linux
        if (systemOs.equals(GlobalConstant.NUM_ZERO)) {
            logDir = logDir + "Logs";
        } else if (systemOs.equals(GlobalConstant.NUM_ONE)) {
            logDir = logDir + "logs";
        } else {
            logDir = logDir + "logs";
        }
        System.out.println("Logging File Path:" + logDir);
        return logDir;
    }

    @Override
    public String getPropertyValue() {
        // 设置日志目录
        return getLoggingFilePath();
    }
}