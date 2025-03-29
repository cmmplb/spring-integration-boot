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
public class VerificationCodeVO {

    /**
     * 服务返回状态 0成功,1：失败
     */
    @XmlElement(name = "rescode")
    private String resCode;

    /**
     * 状态描述
     */
    @XmlElement(name = "resultmsg")
    private String resultMsg;

    /**
     * 验证成功返回验证码
     */
    @XmlElement(name = "verificationCode")
    private String verificationCode;
}
