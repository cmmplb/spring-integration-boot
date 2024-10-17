package io.github.cmmplb.data.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author penglibo
 * @date 2021-09-09 14:57:59
 * @since jdk 1.8
 */

@Data // 此处不能用Data注解, 因为和用户类相互引用, 使用Data注解会重写equals等方法, 如果两个类都用Data注解会报错
@Entity
@Table(name = "role")
//@Getter
//@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time", nullable = false, columnDefinition = "datetime comment '创建时间'")
    private Date createTime;

    /*@ManyToMany(mappedBy = "roleList", // 在3.5.3版本中@JoinColumn与mappingBy是互斥的
            cascade = {*//*CascadeType.ALL*//*},
            fetch = FetchType.EAGER)
    // 假如不定义, jpa会生成“表名1”+“_”+“表名2”的中间表
    *//*@JoinTable(
            // 定义中间表的名称
            name = "account_role",
            // 定义中间表中关联role表的外键名
            joinColumns = {
                    @JoinColumn(name = "role_id",
                            referencedColumnName = "id")
            },
            // 定义中间表中关联account表的外键名
            inverseJoinColumns = {
                    @JoinColumn(name = "account_id",
                            referencedColumnName = "id")
            }
    )*//*
    private List<Account> accountList = new ArrayList<>();*/

    // @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    // 一对多-mappedBy设置关联对象属性名称,fetch决定加载模式, EAGER延迟加载变为立即加载
    // @JoinTable(name = "role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
    // JoinTable:映射中间表
    // JoinColumn:与当前表中主键关联的中间表外键
    // private Set<Menu> menus = new HashSet<>() <>(); // 一个角色关联多个用户

}
