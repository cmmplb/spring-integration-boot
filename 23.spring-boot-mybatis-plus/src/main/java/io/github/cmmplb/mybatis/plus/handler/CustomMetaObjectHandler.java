package io.github.cmmplb.mybatis.plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author penglibo
 * @date 2021-08-22 14:27:23
 * @since jdk 1.8
 * 自定义实现类字段注入-需要在实体类上添加注解 @TableField(.. fill = FieldFill.INSERT) - 新增时填充 -> io.github.cmmplb.mybatisplus.plus.entity.User#createTime
 * 注意事项：
 * -填充原理是直接给entity的属性设置值!!!
 * -注解则是指定该属性在对应情况下必有值,如果无值则入库会是null
 * -MetaObjectHandler提供的默认方法的策略均为:如果属性有值则不覆盖,如果填充值为null则不填充
 * -字段必须声明TableField注解,属性fill选择对应策略,该声明告知Mybatis-Plus需要预留注入SQL字段
 * -填充处理器MyMetaObjectHandler在 Spring Boot 中需要声明@Component或@Bean注入
 * -要想根据注解FieldFill.xxx和字段名以及字段类型来区分必须使用父类的strictInsertFill或者strictUpdateFill方法
 * -不需要根据任何来区分可以使用父类的fillStrategy方法
 */

@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, COL_CREATE_TIME, LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        // 或者
        this.strictInsertFill(metaObject, COL_CREATE_TIME, LocalDateTime::now, LocalDateTime.class); // 起始版本 3.3.3(推荐)
        // 或者
        this.fillStrategy(metaObject, COL_CREATE_TIME, LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, COL_UPDATE_TIME, LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
        // 或者
        this.strictUpdateFill(metaObject, COL_UPDATE_TIME, LocalDateTime::now, LocalDateTime.class); // 起始版本 3.3.3(推荐)
        // 或者
        this.fillStrategy(metaObject, COL_UPDATE_TIME, LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
    }
}
