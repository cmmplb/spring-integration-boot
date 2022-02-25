package com.cmmplb.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class User {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别:0-女;1-男;
     */
    private Byte sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 上海时区
    private Date birthday;

    /**
     * 描述
     */
    @Transient
    private String description;

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_SEX = "sex";

    public static final String COLUMN_BIRTHDAY = "birthday";

}
