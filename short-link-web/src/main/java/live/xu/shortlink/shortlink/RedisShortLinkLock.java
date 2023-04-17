package live.xu.shortlink.shortlink;

import live.xu.shortlink.lock.ShortLinkLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis实现的分布式锁
 * Create by xsg at 2023/04/17 16:39.
 */
@Slf4j
public class RedisShortLinkLock implements ShortLinkLock {

    @Autowired
    private RedissonClient redisson;

    //返回锁实现
    private RLock getLock(String key) {
       return this.redisson.getLock(key);
    }

    @Override
    public boolean lock(String shortLink) {
        try {
            //锁时间，单位：毫秒
            int lockMill = 5000;
            boolean lock = this.getLock(shortLink).tryLock(lockMill, lockMill, TimeUnit.MILLISECONDS);
            log.info("短链接 {} 获取锁结果 {}", shortLink, lock);
            return lock;
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String shortLink) {
        try {
            this.getLock(shortLink).unlock();
        } catch (Exception e) {
            log.error("短链接 {} 解锁异常", shortLink, e);
        }
    }
}
