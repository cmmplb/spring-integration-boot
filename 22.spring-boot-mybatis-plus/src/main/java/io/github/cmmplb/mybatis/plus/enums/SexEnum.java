package io.github.cmmplb.mybatis.plus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author penglibo
 * @date 2021-08-22 14:40:37
 * @since jdk 1.8
 * 通用枚举-自定义字段,设置实体类属性, mybatis-plus会自动转换-重写toString
 * 性别枚举->方式一：枚举实现IEnum接口;方式二：使用@EnumValue注解
 */
public enum SexEnum implements IEnum<Byte> {

    MAN("男", (byte) 1),
    WOMAN("女", (byte) 0);

    private final String name;
    @EnumValue
    private final Byte code;

    SexEnum(String name, Byte code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public Byte getValue() {
        return code;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
