package live.xu.shortlink.lock;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认锁实现
 * Create by xsg at 2023/04/15 12:30.
 */
@Slf4j
public class DefaultShortLinkLock implements ShortLinkLock {

    private int lockMill = 5000;  //锁时间，单位：毫秒
    private final Map<String, Lock> lockMap = new ConcurrentHashMap<>();

    public DefaultShortLinkLock() {
    }

    public DefaultShortLinkLock(int lockMill) {
        this.lockMill = lockMill;
    }

    @Override
    public boolean lock(String shortLink) {
        Assert.notNull(shortLink, "短链接不能为空");
        Lock lock = this.lockMap.computeIfAbsent(shortLink, (k) -> new ReentrantLock());
        try {
            return lock.tryLock(lockMill, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.info("锁被中断", e);
            return false;
        }
    }

    @Override
    public void unlock(String shortLink) {
        Assert.notNull(shortLink, "短链接不能为空");
        Lock lock = this.lockMap.remove(shortLink);
        if (lock != null) {
            lock.unlock();
        }
    }
}
