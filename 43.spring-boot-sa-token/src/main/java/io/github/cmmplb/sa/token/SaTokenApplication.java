package io.github.cmmplb.sa.token;

import cn.dev33.satoken.SaManager;
import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class SaTokenApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SaTokenApplication.class, args);
        System.out.println("启动成功, Sa-Token 配置如下：" + SaManager.getConfig());
    }

}
