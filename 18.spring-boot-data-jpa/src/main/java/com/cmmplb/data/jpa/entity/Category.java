package com.cmmplb.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author penglibo
 * @date 2023-10-11 14:06:30
 * @since jdk 1.8
 */

// @Data // 👆 此处不能用Data注解，因为和用户类相互引用，使用Data注解会重写equals等方法，如果两个类都用Data注解会报错
@Entity // @Entity: 实体类, 必须
@Getter
@Setter
// @Table: 对应数据库中的表, 必须, name=表名, Indexes是声明表里的索引, columnList是索引的列, 同时声明此索引列是否唯一, 默认false
@Table(name = "category", indexes = {@Index(name = "id", columnList = "id", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "category", comment = "分类表")
public class Category {


    // @GeneratedValue： 表明是否自动生成, 必须, strategy也是必写, 指明主键生成策略, 默认是Oracle
    // TABLE： 使用一个特定的数据库表格来保存主键
    // SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。这个值要与generator一起使用，generator 指定生成主键使用的生成器（可能是orcale中自己编写的序列）。
    // IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
    // AUTO： 主键由程序控制，也是GenerationType的默认值。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Id: 指明id列, 必须
    @Id
    // @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    @Column(nullable = false, columnDefinition = "bigint(20) comment '主键id'")
    private Long id;

    // columnDefinition优先级比length高
    @Column(name = "name", nullable = false, length = 64/* , columnDefinition = "varchar(128) comment '分类名称'" */)
    private String name;

    @Column(name = "parent_id", nullable = false, length = 20, columnDefinition = "bigint(20) comment '父级id'")
    private Long parentId;

    @Column(name = "create_time", nullable = false, columnDefinition = "datetime not null default current_timestamp() comment '创建时间'")
    private Date createTime;
}
