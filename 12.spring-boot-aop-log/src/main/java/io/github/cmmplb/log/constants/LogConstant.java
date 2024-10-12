package io.github.cmmplb.log.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author penglibo
 * @date 2022-11-04 13:42:53
 * @since jdk 1.8
 */

public interface LogConstant {

    /**
     * 日志业务类型枚举
     */
    @Getter
    @AllArgsConstructor
    enum LogBusinessTypeEnum {

        /**
         * 日志业务类型枚举
         */
        SAVE((byte) 0, "新增"),

        DELETE((byte) 1, "删除"),

        UPDATE((byte) 2, "修改"),

        QUERY((byte) 3, "查询"),

        IMPORT((byte) 4, "导入"),

        EXPORT((byte) 5, "导出"),
        // more...

        DEFAULT((byte) 0, "新增"),
        ;
        private final Byte type;
        private final String description;
    }

    /**
     * 日志操作类型枚举
     */
    @Getter
    @AllArgsConstructor
    enum LogOperationTypeEnum {

        /**
         * 日志操作类型枚举
         */
        ONE("one", "one"),

        TWO("two", "two"),

        THREE("three", "three"),

        EX("ex", "ex"),

        DEFAULT("", ""),
        ;
        private final String type;
        private final String description;
    }

    /**
     * 日志类型枚举
     */
    @Getter
    @AllArgsConstructor
    enum LogStatusEnum {

        /**
         * 正常
         */
        NORMAL((byte) 0),

        /**
         * 异常
         */
        ABNORMAL((byte) 1);

        /**
         * 类型
         */
        private final Byte type;
    }
}
