package io.github.cmmplb.config.beans;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author penglibo
 * @date 2021-08-13 17:25:01
 * @since jdk 1.8
 */

@Data
@XmlRootElement(name = "custom")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigCustomXml {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "version")
    private String version;
}
