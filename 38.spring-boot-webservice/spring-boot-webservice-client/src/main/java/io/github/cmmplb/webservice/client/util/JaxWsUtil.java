package io.github.cmmplb.webservice.client.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.lang.reflect.Method;

/**
 * @author penglibo
 * @date 2025-03-31 09:14:21
 * @since jdk 1.8
 */

@Slf4j
public class JaxWsUtil {

    public static <T, R> R jaxWsProxy(String wsdl, Class<T> serviceClass, String methodName, Object... methodParams) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress(wsdl);
        jaxWsProxyFactoryBean.setServiceClass(serviceClass);
        jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
        T service = serviceClass.cast(jaxWsProxyFactoryBean.create());
        // 获取 Client 对象
        Client client = ClientProxy.getClient(service);
        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
        // 创建 HTTP 客户端策略
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        // 设置连接超时时间（单位：毫秒）
        httpClientPolicy.setConnectionTimeout(5000);
        // 设置接收超时时间（单位：毫秒）
        httpClientPolicy.setReceiveTimeout(10000);
        // 将策略应用到 HTTP 管道
        httpConduit.setClient(httpClientPolicy);
        R result = null;
        try {
            // 获取方法的参数类型数组
            Class<?>[] paramTypes = new Class[methodParams.length];
            for (int i = 0; i < methodParams.length; i++) {
                paramTypes[i] = methodParams[i].getClass();
            }
            // 通过反射获取要调用的方法
            Method method = serviceClass.getMethod(methodName, paramTypes);
            result = (R) method.invoke(service, methodParams);
        } catch (Exception e) {
            log.error("调用 WebService 服务时出错", e);
        }
        return result;
    }
}
