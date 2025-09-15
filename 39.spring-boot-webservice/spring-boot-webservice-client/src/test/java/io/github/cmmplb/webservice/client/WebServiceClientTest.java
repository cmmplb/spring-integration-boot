package io.github.cmmplb.webservice.client;

import io.github.cmmplb.core.utils.ObjectUtil;
import io.github.cmmplb.core.utils.XmlUtil;
import io.github.cmmplb.webservice.client.domain.dto.DataXmlDTO;
import io.github.cmmplb.webservice.client.domain.dto.MessageDTO;
import io.github.cmmplb.webservice.client.domain.vo.DataXmlVO;
import io.github.cmmplb.webservice.client.domain.vo.MessageVO;
import io.github.cmmplb.webservice.client.service.MessageService;
import io.github.cmmplb.webservice.client.util.JaxWsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class WebServiceClientTest {

    public static final String SERVICE_WSDL = "http://localhost:80/server/services/MessageService?wsdl";

    @Test
    public void test() {

    }

    public static void main(String[] args) throws Exception {
        // 动态创建客户端
        // jaxWsDynamicClient();
        // 反射动态传入接口和方法静态代理调用
        reflection();
        // String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
        //         "<ns2:root xmlns:ns2=\"http://impl.service.server.webservice.cmmplb.github.io/\">\n" +
        //         "    <ns2:dataXml>\n" +
        //         "        <id_1>1</id_1>\n" +
        //         "        <message_1>传递 xml 参数</message_1>\n" +
        //         "    </ns2:dataXml>\n" +
        //         "</ns2:root>";
        // DataXmlDTO dataXmlDTO = XmlUtil.xmlStringToObject(xml, DataXmlDTO.class);
        // System.out.println(dataXmlDTO);
    }

    private static void reflection() {
        MessageVO result = JaxWsUtil.jaxWsProxy(SERVICE_WSDL, MessageService.class,
                "onMessage", new MessageDTO(2L, "你好")
        );
        log.info("返回数据:{}", result);

        // 传递 xml 参数
        DataXmlDTO.DataXml dataXml = new DataXmlDTO.DataXml(1L, "传递 xml 参数");
        DataXmlDTO dto = new DataXmlDTO(dataXml);
        String xmlParams = XmlUtil.objectToXml(dto);
        // xmlParams = xmlParams.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
        log.info("请求参数:{}", xmlParams);
        String xmlResult = JaxWsUtil.jaxWsProxy(SERVICE_WSDL, MessageService.class, "dataXml", "", xmlParams);
        log.info("xmlResult:{}", xmlResult);
        // 去除 xml 中存在的空格
        // xmlResult = xmlResult.replaceAll("\\s*", "");
        DataXmlVO dataXmlVO = XmlUtil.xmlStringToObject(xmlResult, DataXmlVO.class);
        log.info("返回数据:{}", dataXmlVO);
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