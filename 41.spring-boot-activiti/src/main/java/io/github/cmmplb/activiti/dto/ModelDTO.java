package io.github.cmmplb.activiti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@Data
@Schema(name = "ModelDTO", description = "流程设计模型")
public class ModelDTO {

    /**
     * id
     */
    private String id;

    /**
     * 模型关键字
     */
    private String key;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 模型类型
     */
    private String category;

    /**
     * 描述
     */
    private String description;

    private String json_xml;

    private String svg_xml;
}
