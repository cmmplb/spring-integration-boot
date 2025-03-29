package io.github.cmmplb.webservice.server.service.impl;

import io.github.cmmplb.webservice.server.domain.dto.MessageDTO;
import io.github.cmmplb.webservice.server.domain.vo.MessageVO;
import io.github.cmmplb.webservice.server.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public MessageVO onMessage(MessageDTO msg) {
        log.info("接收到消息:{}", msg);
        return new MessageVO(1L,"返回成功消息");
    }
}
