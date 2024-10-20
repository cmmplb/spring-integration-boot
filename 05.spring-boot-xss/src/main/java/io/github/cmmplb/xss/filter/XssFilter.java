package io.github.cmmplb.xss.filter;

import io.github.cmmplb.core.utils.StringUtil;
import io.github.cmmplb.xss.config.properties.XssProperties;
import io.github.cmmplb.xss.wrapper.XssRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author penglibo
 * @date 2021-09-10 16:42:34
 * @since jdk 1.8
 * xss过滤器
 */

@Slf4j
public class XssFilter implements Filter {

    // 是否过滤富文本内容
    private boolean isIncludeRichText = false;

    private final List<String> excludes = new ArrayList<>();

    public boolean enabled = false;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("==========XssFilterInit==========");
        String strEnabled = filterConfig.getInitParameter(XssProperties.COL_ENABLED);
        String strExcludes = filterConfig.getInitParameter(XssProperties.COL_EXCLUDES);
        String isIncludeRichText = filterConfig.getInitParameter(XssProperties.COL_IS_INCLUDE_RICH_TEXT);
        if (StringUtil.isNotBlank(isIncludeRichText)) {
            this.isIncludeRichText = BooleanUtils.toBoolean(isIncludeRichText);
        }
        //将不需要xss过滤的接口添加到列表中
        if (StringUtil.isNotEmpty(strExcludes)) {
            String[] urls = strExcludes.split(",");
            Collections.addAll(excludes, urls);
        }
        if (StringUtil.isNotEmpty(strEnabled)) {
            enabled = Boolean.parseBoolean(strEnabled);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 如果该访问接口在排除列表里面则不拦截
        if (isExcludeUrl(request.getServletPath())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 拦截该url并进行xss过滤
        filterChain.doFilter(new XssRequestWrapper(request, isIncludeRichText), servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean isExcludeUrl(String urlPath) {
        if (!enabled) {
            // 如果xss开关关闭了, 则所有url都不拦截
            return true;
        }
        if (excludes.isEmpty()) {
            return false;
        }
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(urlPath);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
}
