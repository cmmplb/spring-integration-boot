package com.cmmplb.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-09-16 14:53:26
 * @since jdk 1.8
 */

// @Data // 👆 此处不能用Data注解, 因为和用户类相互引用, 使用Data注解会重写equals等方法, 如果两个类都用Data注解会报错
@Entity
@Getter
@Setter
@Table(name = "tag", indexes = {@Index(name = "id", columnList = "id", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "tag", comment = "标签表")
public class Tag {

    @Id
    @Column(nullable = false, columnDefinition = "bigint(20) comment '主键id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(128) comment '标签名称'")
    private String name;

    @Column(name = "account_id", nullable = false, columnDefinition = "bigint(20) comment '账号id'")
    private Long accountId;

    @Column(name = "create_time", nullable = false, columnDefinition = "datetime comment '创建时间'")
    private Date createTime;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId=" + accountId +
                ", createTime=" + createTime +
                '}';
    }
}
