package io.github.cmmplb.websocket.server;

import io.github.cmmplb.core.utils.ErrorUtil;
import io.github.cmmplb.core.utils.LogDirUtil;
import io.github.cmmplb.core.utils.PatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

/**
 * @author plb
 * @date 2021-01-06
 * Websocket基于tomcat实现
 * Component默认是单例模式的, 但spring还是会为每个websocket连接初始化一个端点bean
 * 注意的点：aop代理会导致as it is not annotated with @ServerEndpoint
 */

@Slf4j
@Component
@ServerEndpoint(value = "/console/log/server")
public class ConsoleLogServerEndpoint {

    public static String applicationName;

    /**
     * 连接集合
     */
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Integer> LENGTH_MAP = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        // 添加到集合中
        SESSION_MAP.put(session.getId(), session);
        // 默认从第一行开始
        LENGTH_MAP.put(session.getId(), 1);

        // 获取日志信息
        // noinspection AlibabaAvoidManuallyCreateThread
        new Thread(() -> {
            log.info("任务开始");
            // 复制过来测试
            boolean first = true;
            BufferedReader reader = null;
            while (SESSION_MAP.get(session.getId()) != null) {
                try {
                    // 日志文件, 获取最新的
                    FileReader fileReader = new FileReader(LogDirUtil.getLogDir() + "/" + applicationName + "/info.log");
                    reader = new BufferedReader(fileReader);
                    Object[] lines = reader.lines().toArray();

                    // 只取从上次之后产生的日志
                    Object[] copyOfRange = Arrays.copyOfRange(lines, LENGTH_MAP.get(session.getId()), lines.length);

                    // 对日志进行着色, 更加美观  PS：注意, 这里要根据日志生成规则来操作
                    for (int i = 0; i < copyOfRange.length; i++) {
                        String line = (String) copyOfRange[i];
                        // 先转义
                        line = line.replaceAll("&", "&amp;")
                                .replaceAll("<", "&lt;")
                                .replaceAll(">", "&gt;")
                                .replaceAll("\"", "&quot;");

                        line = line.replace("DEBUG", "<span style='color: blue;'>DEBUG</span>");
                        line = line.replace("INFO", "<span style='color: green;'>INFO</span>");
                        line = line.replace("WARN", "<span style='color: orange;'>WARN</span>");
                        line = line.replace("ERROR", "<span style='color: red;'>ERROR</span>");

                        // 处理类名
                        String[] split = line.split("]");
                        if (split.length >= 2) {
                            String[] split1 = split[1].split("-");
                            if (split1.length >= 2) {
                                line = split[0] + "]" + "<span style='color: #298a8a;'>" + split1[0] + "</span>" + "-" + split1[1];
                            }
                        }
                        Matcher m = PatternUtil.date(line);
                        if (m.find()) {
                            // 找到下标
                            int start = m.start();
                            // 插入
                            StringBuilder sb = new StringBuilder(line);
                            sb.insert(start, "<br/><br/>");
                            line = sb.toString();
                        }
                        copyOfRange[i] = line;
                    }

                    // 存储最新一行开始
                    LENGTH_MAP.replace(session.getId(), lines.length);

                    // 第一次如果太大, 截取最新的200行就够了, 避免传输的数据太大
                    if (first && copyOfRange.length > 200) {
                        copyOfRange = Arrays.copyOfRange(copyOfRange, copyOfRange.length - 200, copyOfRange.length);
                        first = false;
                    }

                    String result = StringUtils.join(copyOfRange, "<br/>");
                    send(session, result);

                    // 休眠一秒
                    // noinspection BusyWait
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // 输出到日志文件中
                    log.error(ErrorUtil.errorInfoToString(e));
                }
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // 输出到日志文件中
                log.error(ErrorUtil.errorInfoToString(e));
            }
            log.info("任务结束");
        }).start();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        // 从集合中删除
        SESSION_MAP.remove(session.getId());
        LENGTH_MAP.remove(session.getId());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        // 输出到日志文件中
        log.error(ErrorUtil.errorInfoToString(error));
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String id = session.getId();
        System.out.println(id);
        System.out.println(session);
        log.info("接收消息");
        log.info(message);
    }

    /**
     * 封装一个send方法, 发送消息到前端
     */
    private void send(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            // 输出到日志文件中
            log.error(ErrorUtil.errorInfoToString(e));
        }
    }
}
