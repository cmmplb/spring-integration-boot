package com.cmmplb.websocket;

import com.cmmplb.core.utils.Base64Util;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.core.utils.ImageUtil;
import com.cmmplb.core.utils.RandomUtil;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;


@SpringBootTest
public class WebSocketTest {

    @Test
    public void test() {

    }

    public static void main(String[] args) throws Exception {
        byte[] byteArray = FileUtil.getByteArray("/Users/penglibo/Downloads/1.png");
        System.out.println(Base64Util.encoder(byteArray));
    }
}