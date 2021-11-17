package com.cmmplb.thymeleaf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 账号
	 */
    private String account;

	/**
	 * 密码
	 */
    private String password;

	/**
	 * 账号
	 */
    private String type;

	/**
	 * 手机号
	 */
    private String phone;
}
