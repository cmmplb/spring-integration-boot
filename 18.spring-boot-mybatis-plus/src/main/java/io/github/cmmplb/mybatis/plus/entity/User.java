package io.github.cmmplb.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.cmmplb.mybatis.plus.enums.SexEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author plb
 * @date 2020/6/15 11:16
 * 用户信息表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
@TableName(value = "user")
@Schema(name = "User", description = "用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // mybatis-plus配置逻辑删除
    public static final String COL_LOGIC_DEL = "logic_del";

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "name")
    @Schema(description = "用户名", example = "小明", required = true)
    private String name;

    /**
     * 性别:0-女;1-男
     */
    @TableField(value = "sex")
    @Schema(description = "性别:0-女;1-男", example = "1") // 使用枚举类型来限制输入值
    private SexEnum sex;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @Schema(description = "手机号", example = "19999999999")
    private String mobile;

    /**
     * 用户状态:0-正常;1-禁用
     */
    @TableField(value = "status")
    @Schema(description = "用户状态:0-正常;1-禁用", example = "0")
    private Byte status;

    /**
     * 乐观锁版本号
     */
    @TableField(value = "version")
    @Schema(description = "乐观锁版本号", example = "1")
    @Version // mybatis-plus乐观锁注解
    private Integer version;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @Schema(description = "更新时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @TableField(value = "deleted")
    @Schema(description = "逻辑删除:0-正常;1-删除", example = "0")
    @TableLogic // 逻辑删除功能、使用mp自带方法删除和查找都会附带逻辑删除功能 (自己写的xml不会)
    private Byte deleted;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_SEX = "sex";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_STATUS = "status";

    public static final String COL_VERSION = "version";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_DELETED = "deleted";
}