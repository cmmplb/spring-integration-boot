package com.cmmplb.canal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Data
@TableName(value = "test")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String name;

    private Date createTime;

}