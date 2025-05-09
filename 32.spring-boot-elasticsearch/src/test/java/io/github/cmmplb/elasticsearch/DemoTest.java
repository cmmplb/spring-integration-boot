package io.github.cmmplb.elasticsearch;

import io.github.cmmplb.core.utils.IpUtil;

/**
 * @author penglibo
 * @date 2023-09-04 14:18:01
 * @since jdk 1.8
 */
public class DemoTest {

    public static void main(String[] args) {
        System.out.println(IpUtil.isLocalPortUsing(80));
        System.out.println(IpUtil.isLocalPortUsing(81));
    }
}
