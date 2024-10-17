package io.github.cmmplb.xss.wrapper;

import io.github.cmmplb.core.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * @author penglibo
 * @date 2021-09-10 16:02:29
 * @since jdk 1.8
 * Jsoup过滤http请求, 防止Xss攻击
 */

@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 是否过滤富文本内容
     */
    private final boolean isIncludeRichText;

    public static final String CONTENT = "content";

    public static final String WITH_HTML = "WithHtml";

    public XssRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 将参数名和参数值都做xss过滤
     * 原始的值通过super.getHeaders(name)获取
     * @param name 参数
     * @return 过滤的值
     */
    @Override
    public String getHeader(String name) {
        name = super.getHeader(name);
        if (StringUtil.isEmpty(name)) {
            return name;
        }
        return Jsoup.clean(name, Safelist.relaxed());
    }

    /**
     * 将参数名和参数值都做xss过滤
     * @param name 参数
     * @return 过滤的值
     */
    @Override
    public String getParameter(String name) {
        if ((CONTENT.equals(name) || name.endsWith(WITH_HTML)) && !isIncludeRichText) {
            return super.getParameter(name);
        }
        name = super.getParameter(name);
        if (StringUtil.isNotBlank(name)) {
            return name;
        }
        return Jsoup.clean(name, Safelist.relaxed());
    }

    /**
     * 将参数名和参数值都做xss过滤
     * @param name 参数
     * @return 过滤的值
     */
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
