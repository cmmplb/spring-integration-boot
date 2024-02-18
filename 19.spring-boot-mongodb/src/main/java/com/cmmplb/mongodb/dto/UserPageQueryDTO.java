package com.cmmplb.mongodb.dto;

import com.cmmplb.core.beans.QueryPageBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author penglibo
 * @date 2021-09-14 11:18:13
 * @since jdk 1.8
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageQueryDTO extends QueryPageBean {

    private static final long serialVersionUID = 7974477431271699528L;

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
}
