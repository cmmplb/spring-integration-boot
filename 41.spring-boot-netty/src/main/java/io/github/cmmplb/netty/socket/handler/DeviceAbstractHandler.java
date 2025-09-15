package io.github.cmmplb.netty.socket.handler;


import io.github.cmmplb.netty.socket.entity.BusinessEntity;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author penglibo
 * @date 2021-06-30 14:09:43
 * @since jdk 1.8
 * 设备端请求服务端Handler
 */

public abstract class DeviceAbstractHandler implements InitializingBean {

    /**
     * 人脸处理设备请求
     * @param reqCmd   请求命令
     * @param dataJson 请求参数
     * @return
     */
    public BusinessEntity business(Byte reqCmd, String dataJson) {
        // 子类未实现该方法就不让其调用抛出异常
        throw new UnsupportedOperationException();
    }

}
