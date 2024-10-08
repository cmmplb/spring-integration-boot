
package com.cmmplb.i18n.utils;

import io.github.cmmplb.core.utils.SpringUtil;
import com.cmmplb.i18n.config.MessageSourceConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化
 */
public class MessageUtils {

    private static MessageSource messageSource;

    static {
        messageSource = SpringUtil.getBean(MessageSourceConfig.MESSAGE_SOURCE);
    }

    public static String getMessage(int code) {
        return getMessage(code, new String[0]);
    }

    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }
}
