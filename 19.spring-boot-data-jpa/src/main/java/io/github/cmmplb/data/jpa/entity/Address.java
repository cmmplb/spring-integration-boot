package com.cmmplb.data.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-09-09 15:41:35
 * @since jdk 1.8
 * 标签
 * at java.lang.String.valueOf(String.java:2994)
 * at java.lang.StringBuilder.append(StringBuilder.java:131)
 * at com.cmmplb.data.jpa.entity.Account.toString(Account.java:18)
 */

// @Data // 👆 此处不能用Data注解, 因为和用户类相互引用, 使用Data注解会重写equals等方法, 如果两个类都用Data注解会报错
@Entity
@Getter
@Setter
@Table(name = "address", indexes = {@Index(name = "id", columnList = "id", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "address", comment = "地址表")
public class Address implements Serializable {

    private static final long serialVersionUID = 7106974362204637670L;

    @Id
    @Column(nullable = false, columnDefinition = "bigint(20) comment '主键id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(128) comment '地址名称'")
    private String name;

    @Column(name = "create_time", nullable = false, columnDefinition = "datetime comment '创建时间'")
    private Date createTime;

    //     * CascadeType包含的类别  级联：给当前设置的实体操作另一个实体的权限
    //     *      CascadeType.ALL 级联所有操作
    //     *      CascadeType.PERSIST 级联持久化（保存）操作
    //     *      CascadeType.MERGE   级联更新（合并）操作
    //     *      CascadeType.REMOVE  级联删除操作
    //     *      CascadeType.REFRESH 级联刷新操作
    //     *      CascadeType.DETACH 级联分离操作,如果你要删除一个实体, 但是它有外键无法删除, 这个级联权限会撤销所有相关的外键关联. 
    /*@ManyToOne(cascade = CascadeType.ALL, targetEntity = Account.class, fetch = FetchType.LAZY)
    // 多对一,多个标签对应一个用户--CascadeType赋予此类操作彼类的权限
    @JoinColumn(name = "account_id") //设置在表中的关联字段(外键)
    // 防止json序列化死循环问题解决
    @JsonBackReference
    private Account account;*/
    // @JoinColumn注解来标识account主键创建到address表的列的名称, 
    //当然没有此注解的时候JPA会根据默认规则生成一个列名称. 

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
