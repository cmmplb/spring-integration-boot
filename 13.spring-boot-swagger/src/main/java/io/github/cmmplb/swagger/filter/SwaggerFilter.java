package io.github.cmmplb.swagger.filter;

import io.github.cmmplb.core.utils.SpringProfileUtil;
import io.github.cmmplb.core.utils.SpringUtil;
import io.github.cmmplb.swagger.configuration.properties.SwaggerProperties;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author penglibo
 * @date 2024-05-20 14:41:21
 * @since jdk 1.8
 * 改写一下这个过滤器
 */

// @Bean搭配使用
// @ConditionalOnBean有则注入；
// @ConditionalOnMissBean没有则注入, 再注册相同类型的Bean就会失败；
// @Conditional条件满足则注入. 

@Configuration
public class SwaggerFilter implements Filter {

    protected static List<Pattern> urlFilters = null;

    static {
        urlFilters = new ArrayList<>();
        urlFilters.add(Pattern.compile(".*?/doc\\.html.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/v2/api-docs.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/v3/api-docs.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/v2/api-docs-ext.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/swagger-resources.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/swagger-ui\\.html.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/swagger-ui.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/swagger-resources/configuration/ui.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/swagger-resources/configuration/security.*", Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Environment environment = SpringUtil.getApplicationContext().getEnvironment();
        String active = environment.getProperty(SpringProfileUtil.SPRING_PROFILES_ACTIVE);
        boolean enabled = Boolean.parseBoolean(environment.getProperty(SwaggerProperties.PREFIX + "." + SwaggerProperties.ENABLED));
        if (SpringProfileUtil.DEVELOPMENT_ENVIRONMENT.contains(active) && enabled) {
            chain.doFilter(request, response);
        } else {
            String uri = ((HttpServletRequest) request).getRequestURI();
            if (!this.match(uri)) {
                chain.doFilter(request, response);
            } else {
                response.setContentType("text/palin;charset=UTF-8");
                PrintWriter pw = response.getWriter();
                pw.write("You do not have permission to access this page");
                pw.flush();
            }
        }
    }

    protected boolean match(String uri) {
        boolean match = false;
        if (uri != null) {
            for (Pattern pattern : urlFilters) {
                if (pattern.matcher(uri).matches()) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }

}
