package io.github.cmmplb.start.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author penglibo
 * @date 2024-10-12 10:53:31
 * @since jdk 1.8
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer age;

    private String name;
}
