package io.github.cmmplb.mybatis.handler.exception;

/**
 * 触发乐观锁异常
 * 后期整合项目中进行捕获
 */
public class LockerException extends RuntimeException {

    private static final long serialVersionUID = -1458375495699446661L;

    public LockerException() {
    }

    public LockerException(String message) {
        super(message);
    }
}
