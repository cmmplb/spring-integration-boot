package io.github.cmmplb.webservice.client.service;

import io.github.cmmplb.webservice.client.domain.dto.MessageDTO;
import io.github.cmmplb.webservice.client.domain.vo.MessageVO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author penglibo
 * @date 2025-03-28 15:17:43
 * @since jdk 1.8
 */

// jdk 9 标记为过时, jdk 11 移除,
@WebService(
        // 指定 Web 服务名称. 未指定则使用类的名称 MessageService
        // name = "MessageService",
        // 指定 Web 服务的目标命名空间, 设置该值后 wsdl 文档会显示参数详细描述. 未指定会使用默认的命名空间: http://impl.service.server.webservice.cmmplb.github.io/
        targetNamespace = "http://impl.service.server.webservice.cmmplb.github.io/",
        // 指定 Web 服务的名称. 若未指定, 会使用类的简单名称加上 Service 后缀
        serviceName = "MessageService"
        // 指定 Web 服务端口的名称, 若未指定, 通常会使用被注解类的简单名称加上 Port 后缀
        // ,portName = ""
        // 指定 WSDL 文档的位置. 若提供了该属性, JAX - WS（Java API for XML Web Services）运行时会从指定位置加载 WSDL 文档, 而非动态生成
        // ,wsdlLocation = ""
        // 指定 Web 服务端点接口的全限定名. 若未指定 JAX - WS 会将被注解类本身当作端点接口
        // ,endpointInterface = "io.github.cmmplb.webservice.server.service.MessageService"
)
public interface MessageService {

    @WebMethod(
            // Web 服务所提供的操作. 如果不指定该属性, 默认会使用 Java 方法的名称作为 wsdl:operation 的名称
            // operationName = ""
            // 该属性用于指定此操作的动作, SOAP 动作是一个 URI, 它通常用于标识 SOAP 请求要执行的具体操作, 在 SOAP 消息的 SOAPAction 头字段中使用
            // action="http://example.com/myAction",
            // 用于标记一个方法是否不暴露为 Web 方法, 如果指定了 exclude 属性为 true, 那么就不能再指定 WebMethod 注解的其他属性, 这个属性不能用在端点接口上
            // exclude = true
    )
    @WebResult(
            // 根据 Web 服务操作的风格有所不同
            // 当操作是 RPC（Remote Procedure Call）风格, 并且 @WebResult.partName 未指定时, 此名称代表 WSDL 中表示返回值的 wsdl:part 的名称
            // 当操作是文档风格或者返回值映射到消息头时, 该名称是表示返回值的 XML 元素的本地名称
            // 设置返回值最外层的 xml 标签名, 实体类中的 @XmlRootElement(name = "root") 会被覆盖 ( 就算不设置, soap 也会默认覆盖为 <return></return> 标签)
            // 若操作是文档风格且参数风格为 BARE, 默认值是 @WebParam.operationName + "Response"
            // 其他情况下, 默认值是 "return"
            name = "resultInfo"
            // 表示该返回值的 wsdl:part 的名称, 此属性仅在操作是 RPC 风格, 或者操作是文档风格且参数风格为 BARE 时使用, 默认值为 @WebResult.name 的值
            // partName = "",
            // 返回值的 XML 命名空间
            // targetNamespace = "",
            // 若为 true, 表示返回值是从消息头而非消息体中获取的, 若为 false, 则表示参数从消息体中获取
            // header = true
    )
    MessageVO onMessage(@WebParam(
            // 根据 Web 服务操作的风格有所不同
            // 当操作是 RPC（Remote Procedure Call）风格, 并且 @WebResult.partName 未指定时, 此名称代表 WSDL 中表示返回值的 wsdl:part 的名称
            // 当操作是文档风格或者返回值映射到消息头时, 该名称是表示返回值的 XML 元素的本地名称
            // 设置返回值最外层的 xml 标签名, 实体类中的 @XmlRootElement(name = "root") 会被覆盖 ( 就算不设置, soap 也会默认覆盖为 <return></return> 标签)
            // 若操作是文档风格且参数风格为 BARE，默认值是 @WebMethod.operationName
            // 其他情况下, 默认值是 argN, 其中 N 表示参数在方法签名中的索引( 从 arg0 开始 )
            name = "paramdata"
            // 表示该参数的 wsdl:part 的名称, 此属性仅在操作是 RPC 风格, 或者操作是文档风格且参数风格为 BARE 时使用, 默认值为 @WebResult.name 的值
            // partName = "",
            // 参数的 XML 命名空间
            // targetNamespace = "",
            // OUT 和 INOUT 模式仅适用于符合 Holder 类型定义的参数类型. Holder 类型的参数必须指定为 OUT 或 INOUT 模式, 默认值: 若参数是 Holder 类型, 默认值为 INOUT. 若参数不是 Holder 类型, 默认值为 IN
            // mode = WebParam.Mode.IN,
            // 若为 true, 表示参数是从消息头而非消息体中获取的, 若为 false, 则表示参数从消息体中获取
            // header = true
    ) MessageDTO msg);

    /**
     * 测试字符串传递 xml 格式数据
     * @param in0
     * @param dataXml
     * @return
     */
    @WebMethod
    String dataXml(@WebParam(name = "in0", targetNamespace = "http://impl.service.server.webservice.cmmplb.github.io/") String in0,
                 @WebParam(name = "dataXml", targetNamespace = "http://impl.service.server.webservice.cmmplb.github.io/") String dataXml);
}
