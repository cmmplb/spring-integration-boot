package io.github.cmmplb.mybatis.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-04-13 16:00:50
 * 用户详情信息表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
@ApiModel(value = "UserInfo", description = "用户详情信息表")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键-对应用户信息表id
     */
    @ApiModelProperty(value = "主键-对应用户信息表id", example = "1")
    private Long id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", example = "https://cn.bing.com/images/search?view=detailV2&ccid=njDqTtGZ&id=0A45E8E5A7B1D0D9B1801F5E08504064E81EA201&thid=OIP.njDqTtGZsXB5NGb7xzGjkAHaEK&mediaurl=https%3a%2f%2f2img.hitv.com%2fpreview%2fsp_images%2f2020%2f7%2f24%2fdongman%2f340565%2f9424879%2f20200724090241167.jpg&exph=484&expw=860&q=Gon%e7%9a%84%e6%97%b1%e7%8d%ad%e7%ac%ac3%e5%ad%a3&simid=608039306201944876&FORM=IRPRST&ck=7D080BE3C6EF9E4A2170013474842A99&selectedIndex=0")
    private String icon;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @ApiModelProperty(value = "逻辑删除:0-正常;1-删除", example = "0")
    private Byte deleted;
}