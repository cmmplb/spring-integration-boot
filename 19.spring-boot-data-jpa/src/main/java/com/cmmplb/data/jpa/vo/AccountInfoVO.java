package com.cmmplb.data.jpa.vo;

import io.github.cmmplb.core.utils.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author penglibo
 * @date 2023-10-11 13:36:53
 * @since jdk 1.8
 */

@Data
@NoArgsConstructor
public class AccountInfoVO {

    private Long id;

    private String name;

    private String password;

    private String email;

    private Date createTime;

    private Long tagId;

    private String tagName;

    private String formatCreateTime;

    private Object accountName;

    private String categoryOneName;

    private String categoryTwoName;

    public AccountInfoVO(Long id, String name, String password, Date createTime, Long tagId, String tagName, String formatCreateTime, String accountName, String categoryOneName, String categoryTwoName) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.createTime = DateUtil.parse(createTime, DateUtil.FORMAT_DATE_YYYY_MM_DD);
        this.tagId = tagId;
        this.tagName = tagName;
        this.formatCreateTime = formatCreateTime;
        this.accountName = accountName;
        this.categoryOneName = categoryOneName;
        this.categoryTwoName = categoryTwoName;
        System.out.println("使用构造函数");
    }
}
