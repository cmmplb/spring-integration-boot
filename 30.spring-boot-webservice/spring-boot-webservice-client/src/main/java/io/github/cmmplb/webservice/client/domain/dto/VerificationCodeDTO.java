package io.github.cmmplb.webservice.client.domain.dto;

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
public class VerificationCodeDTO {

    /**
     * 销售品
     */
    @XmlElement(name = "sales_Id")
    private String salesId;

    /**
     * 平台标示
     */
    @XmlElement(name = "buss_terrace")
    private String bussTerrace;

    /**
     * 手机号码
     */
    @XmlElement(name = "telephone")
    private String telephone;
}
