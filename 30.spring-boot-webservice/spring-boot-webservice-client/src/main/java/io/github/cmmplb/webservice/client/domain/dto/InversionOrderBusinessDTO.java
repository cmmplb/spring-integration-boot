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
public class InversionOrderBusinessDTO {

    /**
     * 订单ID号
     */
    @XmlElement(name = "orderId")
    private String orderId;

    /**
     * 注册号码
     */
    @XmlElement(name = "registerNumber")
    private String registerNumber;

    /**
     * 所属地州
     */
    @XmlElement(name = "areaId")
    private String areaId;

    /**
     * 姓名
     */
    @XmlElement(name = "customerName")
    private String customerName;

    /**
     * 联系号码
     */
    @XmlElement(name = "telephone")
    private String telephone;

    /**
     * 状态
     */
    @XmlElement(name = "status")
    private String status;

    /**
     * 操作类型
     */
    @XmlElement(name = "operType")
    private String operType;

    /**
     * 短信接收号码
     */
    @XmlElement(name = "smsReceiveNumber")
    private String smsReceiveNumber;

    /**
     * 产品
     */
    @XmlElement(name = "porductId")
    private String productId;

    /**
     * 资费档次
     */
    @XmlElement(name = "salesId")
    private String salesId;

    /**
     * 平台id
     */
    @XmlElement(name = "bussTerrace")
    private String bussTerrace;

    /**
     * 验证码
     */
    @XmlElement(name = "verificationCode")
    private String verificationCode;

    /**
     * 注册来源
     */
    @XmlElement(name = "orderType")
    private String orderType;

    /**
     * 注销来源
     */
    @XmlElement(name = "logoffType")
    private String logoffType;

    /**
     * 注册时间
     */
    @XmlElement(name = "regisTime")
    private String registerTime;

    /**
     * 操作时间
     */
    @XmlElement(name = "operTime")
    private String operateTime;

    /**
     * 密码
     */
    @XmlElement(name = "orderPassword")
    private String orderPassword;

    /**
     * 备用字段1
     */
    @XmlElement(name = "remark1")
    private String remark1;

    /**
     * 备用字段2
     */
    @XmlElement(name = "remark2")
    private String remark2;

}
