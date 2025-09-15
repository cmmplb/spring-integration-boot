package io.github.cmmplb.xss.wrapper;

import io.github.cmmplb.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

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
        return cleanHtml(name);
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
        return cleanHtml(name);
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
            values[i] = cleanHtml(values[i]);
        }
        return values;
    }

    /**
     * 清理HTML内容，防止XSS攻击
     * @param dirtyHtml 原始HTML内容
     * @return 清理后的安全内容
     */
    public static String cleanHtml(String dirtyHtml) {
        if (dirtyHtml == null || dirtyHtml.isEmpty()) {
            return "";
        }
        // 使用预定义的Safelist并自定义扩展
        Safelist safelist = Safelist.basic()
                // 新增允许的标签
                .addTags("img", "div", "span");
        // 图片标签允许的属性
        Safelist images = Safelist.basicWithImages();
        // 清理HTML并确保输出安全
        return Jsoup.clean(dirtyHtml, Safelist.relaxed());
    }
}
