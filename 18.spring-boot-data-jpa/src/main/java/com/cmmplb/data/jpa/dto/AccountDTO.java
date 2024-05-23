package com.cmmplb.data.jpa.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author penglibo
 * @date 2021-09-09 16:26:07
 * @since jdk 1.8
 */

@Data
public class AccountDTO {

    private Long id;

    private String name;
}
