package io.github.cmmplb.spark.entity;

/**
 * @author penglibo
 * @date 2025-06-05 15:56:09
 * @since jdk 1.8
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class User {

    private Long id;

    private String name;

    private Integer age;

    private List<Tag> tagList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tag {
        private Long id;

        private String name;

        private String category;

        private Integer number;
    }
}
