package com.cmmplb.dynamic.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @author penglibo
* @date 2021-08-06 10:47:06
* @since jdk 1.8
*/

/**
 * 商品信息表
 */
@ApiModel(value = "com-cmmplb-dynamic-datasource-entity-Goods")
@Data
@TableName(value = "goods")
public class Goods implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 商品名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品库存
     */
    @TableField(value = "`count`")
    @ApiModelProperty(value = "商品库存")
    private Integer count;

    /**
     * 商品金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "商品金额")
    private Integer amount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_COUNT = "count";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}