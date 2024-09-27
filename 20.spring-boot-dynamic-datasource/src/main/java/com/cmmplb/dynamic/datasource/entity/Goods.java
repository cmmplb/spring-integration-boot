package com.cmmplb.dynamic.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Goods", description = "商品信息表")
@Data
@TableName(value = "goods")
public class Goods implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id", description = "主键id", example = "1")
    private Long id;

    /**
     * 商品名称
     */
    @TableField(value = "`name`")
    @Schema(name = "name", description = "商品名称")
    private String name;

    /**
     * 商品库存
     */
    @TableField(value = "`count`")
    @Schema(name = "count", description = "商品库存")
    private Integer count;

    /**
     * 商品金额
     */
    @TableField(value = "amount")
    @Schema(name = "amount", description = "商品金额")
    private Integer amount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @Schema(name = "createTime", description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @Schema(name = "updateTime", description = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_COUNT = "count";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}