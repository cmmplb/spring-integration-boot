package com.cmmplb.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author penglibo
 * @date 2021-09-14 09:57:50
 * @since jdk 1.8
 */

@Data
@Document(collection = "user")
@ApiModel(value = "User", description = "用户信息")
public class User {

    /**
     * id
     */
    @Id
    @ApiModelProperty(value = "主键", example = "1")
    private String id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "小明", required = true)
    private String name;

    /**
     * 性别:0-女;1-男;
     */
    @ApiModelProperty(value = "性别:0-女;1-男;", example = "1", required = true)
    private Byte sex;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", example = "2021-01-01", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 上海时区
    private Date birthday;

    /**
     * 描述
     */
    @Transient
    @ApiModelProperty(value = "描述", example = "描述")
    private String description;

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_SEX = "sex";

    public static final String COLUMN_BIRTHDAY = "birthday";

}
