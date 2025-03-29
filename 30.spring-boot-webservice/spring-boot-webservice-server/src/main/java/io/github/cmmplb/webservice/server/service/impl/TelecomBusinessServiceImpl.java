package io.github.cmmplb.webservice.server.service.impl;

import io.github.cmmplb.webservice.server.domain.dto.OrderBusinessDTO;
import io.github.cmmplb.webservice.server.domain.vo.MessageVO;
import io.github.cmmplb.webservice.server.domain.vo.OrderBusinessVO;
import io.github.cmmplb.webservice.server.service.TelecomBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @author penglibo
 * @date 2025-03-30 03:50:22
 * @since jdk 1.8
 */

// jdk 9 标记为过时, jdk 11 移除,
@WebService(
        // 指定 Web 服务的目标命名空间, 设置该值后 wsdl 文档会显示参数详细描述. 未指定会使用默认的命名空间: http://impl.service.server.webservice.cmmplb.github.io/
        targetNamespace = "http://impl.service.server.webservice.cmmplb.github.io/",
        // 指定 Web 服务的名称. 若未指定, 会使用类的简单名称加上 Service 后缀
        serviceName = "TelecomBusinessService"
)
@Slf4j
@Service
public class TelecomBusinessServiceImpl implements TelecomBusinessService {

    @Override
    public OrderBusinessVO orderBusiness(OrderBusinessDTO dto) {
        log.info("接收到消息:{}", dto);
        return new OrderBusinessVO("0","订购同步成功");
    }
}
