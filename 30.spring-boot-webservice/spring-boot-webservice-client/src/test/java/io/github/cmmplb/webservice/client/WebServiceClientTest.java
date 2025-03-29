package io.github.cmmplb.webservice.client;

import io.github.cmmplb.core.utils.MapObjectUtil;
import io.github.cmmplb.core.utils.ObjectUtil;
import io.github.cmmplb.webservice.client.domain.dto.InversionOrderBusinessDTO;
import io.github.cmmplb.webservice.client.domain.dto.MessageDTO;
import io.github.cmmplb.webservice.client.domain.dto.VerificationCodeDTO;
import io.github.cmmplb.webservice.client.domain.vo.InversionOrderBusinessVO;
import io.github.cmmplb.webservice.client.domain.vo.MessageVO;
import io.github.cmmplb.webservice.client.domain.vo.VerificationCodeVO;
import io.github.cmmplb.webservice.client.service.HaobaiService;
import io.github.cmmplb.webservice.client.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@SpringBootTest
public class WebServiceClientTest {

    public static final String SERVICE_WSDL = "http://localhost:80/server/services/MessageService?wsdl";
    public static final String VERIFICATION_CODE_SERVICE_WSDL = "http://135.0.120.89:19083/haobai-interface/services/DoVerificationCode?wsdl";
    public static final String INVERSION_ORDER_BUSINESS_SERVICE_WSDL = "http://135.0.120.89:19083/haobai-interface/services/DoInversionOrderBusiness?wsdl";

    @Test
    public void test() {

    }

    public static void main(String[] args) throws Exception {
        // 动态创建客户端
        // jaxWsDynamicClient();
        // 静态代理
        // jaxWsProxy();
        // 反射动态传入接口和方法调用
        reflection();
    }

    private static void reflection() {
        MessageVO result = jaxWsProxy(SERVICE_WSDL, MessageService.class,
                "onMessage", new MessageDTO(2L, "你好")
        );
        log.info("vo:{}", result);

        // VerificationCodeVO verificationCodeVO = jaxWsProxy(VERIFICATION_CODE_SERVICE_WSDL, HaobaiService.class,
        //         "verificationCode", new VerificationCodeDTO()
        // );
        // log.info("verificationCodeVO:{}", verificationCodeVO);
        //
        // InversionOrderBusinessVO inversionOrderBusinessVO = jaxWsProxy(INVERSION_ORDER_BUSINESS_SERVICE_WSDL, HaobaiService.class,
        //         "inversionOrderBusinessVO", new InversionOrderBusinessDTO()
        // );
        // log.info("inversionOrderBusinessVO:{}", inversionOrderBusinessVO);
    }

    public static <T, R> R jaxWsProxy(String wsdl, Class<T> serviceClass, String methodName, Object... methodParams) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress(wsdl);
        jaxWsProxyFactoryBean.setServiceClass(serviceClass);
        jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
        T service = serviceClass.cast(jaxWsProxyFactoryBean.create());
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
            log.error("调用 Web 服务时出错", e);
        }
        return result;
    }

    private static void jaxWsProxy() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress(SERVICE_WSDL);
        // 映射接口
        jaxWsProxyFactoryBean.setServiceClass(MessageService.class);
        MessageService messageService = (MessageService) jaxWsProxyFactoryBean.create();
        MessageVO result = messageService.onMessage(new MessageDTO(2L, "你好"));
        log.info("result:{}", result);
    }

    // 动态代理创建在 jdk9 以上会报错 is in unnamed module of loader 'app', 需要在根目录创建  module-info.java 开放反射
    private static void jaxWsDynamicClient() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(SERVICE_WSDL);
        Object[] result = client.invoke("onMessage", new MessageDTO(2L, "你好"));
        Object o = result[0];
        MessageVO vo = ObjectUtil.cast(o);
        log.info("result:{}", o);
        log.info("vo:{}", vo);
    }
}