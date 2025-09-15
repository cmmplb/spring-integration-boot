package io.github.cmmplb.webservice.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author penglibo
 * @date 2025-03-28 15:17:43
 * @since jdk 1.8
 */

@Data
// 注意这个注解要保证类有无参构造函数, 并且所有的字段都有对应的 getter 和 setter 方法
@XmlRootElement(name = "root", namespace = "http://impl.service.server.webservice.cmmplb.github.io/")
// 类的两个属性具有相同名称 lombok( @Data ), 1 counts of IllegalAnnotationExceptions 需要从 get 方法上加 @XmlTransient 来避免此错误, 但由于使用的是 lombok 的 @Data, 也可以使用 @XmlAccessorType
// XmlAccessType.FIELD：映射这个类中的所有字段到XML
// XmlAccessType.PROPERTY：映射这个类中的属性（get/set方法）到XML
// XmlAccessType.PUBLIC_MEMBER：将这个类中的所有public的field或property同时映射到XML（默认）
// XmlAccessType.NONE：不映射
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class DataXmlDTO {

    @XmlElement(name = "dataXml", namespace = "http://impl.service.server.webservice.cmmplb.github.io/")
    private DataXml dataXml;

    @Data
    @ToString
    // @XmlRootElement(name = "dataXml", namespace = "http://impl.service.server.webservice.cmmplb.github.io/")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataXml {

        // 对应 xml 标签 <id_1></id_1>
        @XmlElement(name = "id_1")
        private Long id;

        @XmlElement(name = "message_1")
        private String message;
    }
}