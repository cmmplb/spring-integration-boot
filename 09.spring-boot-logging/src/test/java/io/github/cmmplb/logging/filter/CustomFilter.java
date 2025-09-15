package io.github.cmmplb.logging.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class CustomFilter extends Filter<ILoggingEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CustomFilter.class);
    private boolean enabled = true; // 默认启用
    private String condition; // 条件表达式或参数
    private FilterReply decision = FilterReply.NEUTRAL; // 默认行为：不做改变
 
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (enabled) { // 根据条件判断是否启用过滤逻辑
            // 这里可以加入更复杂的条件判断逻辑，例如基于事件属性等。
            // 例如，根据日志级别决定是否过滤：
            if (event.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.WARN)) {
                return FilterReply.ACCEPT; // 接受警告及以上级别的日志事件
            } else {
                return FilterReply.DENY; // 拒绝其他级别的日志事件
            }
        } else {
            return FilterReply.NEUTRAL; // 如果过滤器不启用，则不做任何操作
        }
    }
    // Getter和Setter方法...
}