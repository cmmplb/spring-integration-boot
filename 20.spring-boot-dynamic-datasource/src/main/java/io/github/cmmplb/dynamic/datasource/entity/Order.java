package io.github.cmmplb.dynamic.datasource.entity;

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
 * 订单表
*/

@Schema(name = "Order", description = "订单表")
@Data
@TableName(value = "`order`")
public class Order implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id", description = "主键id", example = "1")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @Schema(name = "userId", description = "用户id")
    private Long userId;

    /**
     * 商品id
     */
    @TableField(value = "goods_id")
    @Schema(name = "goodsId", description = "商品id")
    private Long goodsId;

    /**
     * 商品金额
     */
    @TableField(value = "amount")
    @Schema(name = "amount", description = "商品金额")
    private Integer amount;

    /**
     * 商品订单状态:1-未支付;2-已支付
     */
    @TableField(value = "`status`")
    @Schema(name = "status", description = "商品订单状态:1-未支付;2-已支付")
    private Byte status;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}