package com.cmmplb.data.jpa.vo;

import lombok.Data;

/**
 * @author penglibo
 * @date 2021-09-16 15:11:04
 * @since jdk 1.8
 */

@Data
public class AccountTagVO {

    private Long accountId;

    private Long tagId;

    private String tagName;
}
