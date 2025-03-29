package io.github.cmmplb.webservice.server.service;

import io.github.cmmplb.webservice.server.domain.dto.MessageDTO;
import io.github.cmmplb.webservice.server.domain.dto.OrderBusinessDTO;
import io.github.cmmplb.webservice.server.domain.vo.MessageVO;
import io.github.cmmplb.webservice.server.domain.vo.OrderBusinessVO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author penglibo
 * @date 2025-03-30 03:45:09
 * @since jdk 1.8
 */

// jdk 9 标记为过时, jdk 11 移除,
@WebService(
        // 指定 Web 服务的目标命名空间, 设置该值后 wsdl 文档会显示参数详细描述. 未指定会使用默认的命名空间: http://impl.service.server.webservice.cmmplb.github.io/
        targetNamespace = "http://impl.service.server.webservice.cmmplb.github.io/",
        // 指定 Web 服务的名称. 若未指定, 会使用类的简单名称加上 Service 后缀
        serviceName = "TelecomBusinessService"
)
public interface TelecomBusinessService {

    @WebMethod
    @WebResult(
            // 根据 Web 服务操作的风格有所不同
            // 当操作是 RPC（Remote Procedure Call）风格, 并且 @WebResult.partName 未指定时, 此名称代表 WSDL 中表示返回值的 wsdl:part 的名称
            // 当操作是文档风格或者返回值映射到消息头时, 该名称是表示返回值的 XML 元素的本地名称
            // 设置返回值最外层的 xml 标签名, 实体类中的 @XmlRootElement(name = "root") 会被覆盖 ( 就算不设置, soap 也会默认覆盖为 <return></return> 标签)
            // 若操作是文档风格且参数风格为 BARE, 默认值是 @WebParam.operationName + "Response"
            // 其他情况下, 默认值是 "return"
            name = "resultInfo"
    )
    OrderBusinessVO orderBusiness(@WebParam(
            // 根据 Web 服务操作的风格有所不同
            // 当操作是 RPC（Remote Procedure Call）风格, 并且 @WebResult.partName 未指定时, 此名称代表 WSDL 中表示返回值的 wsdl:part 的名称
            // 当操作是文档风格或者返回值映射到消息头时, 该名称是表示返回值的 XML 元素的本地名称
            // 设置返回值最外层的 xml 标签名, 实体类中的 @XmlRootElement(name = "root") 会被覆盖 ( 就算不设置, soap 也会默认覆盖为 <return></return> 标签)
            // 若操作是文档风格且参数风格为 BARE，默认值是 @WebMethod.operationName
            // 其他情况下, 默认值是 argN, 其中 N 表示参数在方法签名中的索引( 从 arg0 开始 )
            name = "paramdata"
    ) OrderBusinessDTO dto);
}
