package io.github.cmmplb.activiti.util;

import io.github.cmmplb.core.utils.ServletUtil;

/**
 * @author penglibo
 * @date 2023-11-16 11:24:42
 * @since jdk 1.8
 */
public class SecurityUtil {

    public static Long getUserId() {
        // 这里为了简化, 用户是前端请求头传的用户id, 业务中要接入用户逻辑, 比如token凭证获取用户信息逻辑
        String userId = ServletUtil.getHeader("User-Id");
        return Long.parseLong(userId);
    }
}
