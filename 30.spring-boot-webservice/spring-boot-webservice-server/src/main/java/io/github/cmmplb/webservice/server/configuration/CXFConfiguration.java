package io.github.cmmplb.webservice.server.configuration;

import io.github.cmmplb.webservice.server.service.MessageService;
import io.github.cmmplb.webservice.server.service.TelecomBusinessService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author penglibo
 * @date 2025-03-28 15:17:43
 * @since jdk 1.8
 */

@Configuration
public class CXFConfiguration {

    // todo: 通过配置动态注册 Endpoint

    @Autowired
    private MessageService messageService;

    @Autowired
    private TelecomBusinessService telecomBusinessService;

    @Bean
    public ServletRegistrationBean<CXFServlet> getRegistrationBean() {
        // 根路径
        return new ServletRegistrationBean<>(new CXFServlet(), "/server/services/*");
    }

    @Bean
    public Endpoint endPoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), this.messageService);
        // 二级路径
        endpoint.publish("/MessageService");
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getInInterceptors().add(new LoggingOutInterceptor());
        return endpoint;
    }

    @Bean
    public Endpoint telecomBusinessServiceEndPoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), this.telecomBusinessService);
        // 二级路径
        endpoint.publish("/TelecomBusinessService");
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getInInterceptors().add(new LoggingOutInterceptor());
        return endpoint;
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
}
