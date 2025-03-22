package io.github.cmmplb.cache.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DistributedLockExample {
    private final RedissonClient redisson;
 
    public DistributedLockExample(RedissonClient redisson) {
        this.redisson = redisson;
    }
 
    public void useLock() {
        // 获取锁的名称，通常使用一个唯一的键名
        RLock lock = redisson.getLock("myLock");
        try {
            // 尝试获取锁，最多等待3秒，上锁以后10秒自动解锁（如果获取锁失败，则会抛出异常）
            boolean isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (isLocked) {
                try {
                    // 执行业务逻辑...
                    System.out.println("Lock acquired, executing critical section.");
                    // 这里放置你的业务代码...
                } finally {
                    // 确保释放锁，即使在发生异常的情况下也能释放锁。
                    lock.unlock();
                }
            } else {
                System.out.println("Could not acquire the lock.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 设置中断标志位，以便其他线程知道发生了中断。
            System.err.println("Thread was interrupted while waiting for the lock.");
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) { // 检查是否当前线程持有锁以避免重复解锁。
                lock.unlock(); // 确保无论如何都释放锁。
            }
        }
    }
}