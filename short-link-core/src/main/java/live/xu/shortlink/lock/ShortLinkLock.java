package live.xu.shortlink.lock;

/**
 * 锁实现，在生成短链接或者从数据库中加载短链接时使用到的锁
 * Create by xsg at 2023/04/15 09:50.
 */
public interface ShortLinkLock {

    /**
     * 获取锁
     * @param shortLink 短链接
     * @return 获取到锁，返回 true ；否则返回 false
     */
    boolean lock(String shortLink);

    /**
     * 解锁
     * @param shortLink 短链接
     */
    void unlock(String shortLink);
}
