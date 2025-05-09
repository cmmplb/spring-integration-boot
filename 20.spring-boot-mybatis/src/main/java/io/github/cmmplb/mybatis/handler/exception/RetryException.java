package io.github.cmmplb.mybatis.handler.exception;

/**
 * 乐观锁异常重试失败异常
 * 后期整合项目中进行捕获
 */
public class RetryException extends RuntimeException {

    private static final long serialVersionUID = -1458375495699446661L;

    public RetryException() {
    }

    public RetryException(String message) {
        super(message);
    }
}
