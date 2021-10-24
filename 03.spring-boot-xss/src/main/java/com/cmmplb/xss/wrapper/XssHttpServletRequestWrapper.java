package com.cmmplb.xss.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author penglibo
 * @date 2021-09-10 16:02:29
 * @since jdk 1.8
 * Jsoup过滤http请求，防止Xss攻击
 */

@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    // 是否过滤富文本内容
    private boolean isIncludeRichText;

    public static final String CONTENT = "content";

    public static final String WITH_HTML = "WithHtml";

    /**
     * Constructs a request object wrapping the given request.
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 将参数名和参数值都做xss过滤
     * 原始的值通过super.getHeaders(name)获取
     * @param name
     * @return
     */
    @Override
    public String getHeader(String name) {
        name = super.getHeader(name);
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        return Jsoup.clean(name, Safelist.relaxed());
    }

    /**
     * 将参数名和参数值都做xss过滤
     */
    @Override
    public String getParameter(String name) {
        if ((CONTENT.equals(name) || name.endsWith(WITH_HTML)) && !isIncludeRichText) {
            return super.getParameter(name);
        }
        name = super.getParameter(name);
        if (StringUtils.isNotBlank(name)) {
            return name;
        }
        return Jsoup.clean(name, Safelist.relaxed());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (null == values) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = Jsoup.clean(values[i], Safelist.relaxed());
        }
        return values;
    }
}
