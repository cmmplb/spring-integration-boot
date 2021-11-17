package com.cmmplb.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-09 11:53:17
 * @since jdk 1.8
 * Cannot resolve table的话连接上数据库之后选择
 * View->Tool Windows–>Persistence->右键entity-Assign Data Sources指定数据源
 */

// @Data // 此处不能用Data注解，因为和用户类相互引用，使用Data注解会重写equals等方法，如果两个类都用Data注解会报错
@Getter
@Setter
@Entity    // @Entity: 实体类, 必须
// @Table: 对应数据库中的表, 必须, name=表名, Indexes是声明表里的索引, columnList是索引的列, 同时声明此索引列是否唯一, 默认false
@Table(name = "account", indexes = {
        @Index(name = "id", columnList = "id", unique = true),
})
@org.hibernate.annotations.Table(appliesTo = "account", comment = "账号表")
public class Account {

    @Id // @Id: 指明id列, 必须
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue： 表明是否自动生成, 必须, strategy也是必写, 指明主键生成策略, 默认是Oracle
    // TABLE： 使用一个特定的数据库表格来保存主键
    // SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。这个值要与generator一起使用，generator 指定生成主键使用的生成器（可能是orcale中自己编写的序列）。
    // IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
    // AUTO： 主键由程序控制，也是GenerationType的默认值。
    private Long id;

    @Column(name = "name", nullable = false) // @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    private String name;

    private String password;

    @Transient // 不需要映射的字段。
    private String email;

    @Column(name = "create_time", nullable = false, columnDefinition = "datetime comment '创建时间'")
    private Date createTime;

    /*@OneToMany(*//*cascade = CascadeType.ALL,*//* mappedBy = "account", fetch = FetchType.LAZY)    // 一对多-mappedBy设置关联对象属性名称
    private List<Address> addressList = new ArrayList<>();*/

    // 在3.5.3版本中@JoinColumn与mappingBy是互斥的
    // 一对多-mappedBy设置关联对象属性名称,fetch决定加载模式，EAGER延迟加载变为立即加载
    /*@ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // 假如不定义，jpa会生成“表名1”+“_”+“表名2”的中间表--这里把中间表的维护端放到了role
    @JoinTable(
            // 定义中间表的名称
            name = "account_role",
            // 定义中间表中关联account表的外键名
            joinColumns = {
                    @JoinColumn(name = "account_id",
                            referencedColumnName = "id")
            },
            // 定义中间表中关联role表的外键名
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id",
                            referencedColumnName = "id")
            }
    )
    private List<Role> roleList = new ArrayList<>();*/

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
