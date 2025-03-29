package io.github.cmmplb.webservice.client.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class InversionOrderBusinessVO {

    @XmlElement(name = "id_2")
    private Long id;

    @XmlElement(name = "message_2")
    private String message;
}
