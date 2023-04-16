package live.xu.shortlink;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import live.xu.shortlink.cache.local.LoadingCacheLocalCache;
import live.xu.shortlink.cache.local.LocalCache;
import live.xu.shortlink.cache.remote.RemoteCache;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.core.ShortLinkGenerator;
import live.xu.shortlink.core.ShortLinkRequest;
import live.xu.shortlink.core.UrlMurmurHashAlgo;
import live.xu.shortlink.exception.ShortLinkExistsException;
import live.xu.shortlink.exception.UnGetLockException;
import live.xu.shortlink.lock.ShortLinkLock;
import live.xu.shortlink.storage.ShortLinkStorage;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 短链接执行器
 * Create by xsg at 2023/04/15 09:45.
 */
@Slf4j
public class ShortLinkExecutor {
    //域名
    private final String domain;
    //缓存实现
    private final LocalCache localCache;
    //锁实现
    private final ShortLinkLock shortLinkLock;
    //短链接存储
    private final ShortLinkStorage shortLinkStorage;
    //短链接生成器
    private final ShortLinkGenerator shortLinkGenerator;

    public ShortLinkExecutor(String domain,
                             LocalCache localCache,
                             ShortLinkLock shortLinkLock,
                             ShortLinkStorage shortLinkStorage) {
        this(domain, localCache, shortLinkLock, shortLinkStorage, new ShortLinkGenerator(new UrlMurmurHashAlgo()));
    }

    public ShortLinkExecutor(String domain,
                             RemoteCache remoteCache,
                             ShortLinkLock shortLinkLock,
                             ShortLinkStorage shortLinkStorage) {
        this(domain, new LoadingCacheLocalCache(remoteCache), shortLinkLock, shortLinkStorage, new ShortLinkGenerator(new UrlMurmurHashAlgo()));
    }

    public ShortLinkExecutor(String domain,
                             LocalCache localCache,
                             ShortLinkLock shortLinkLock,
                             ShortLinkStorage shortLinkStorage,
                             ShortLinkGenerator shortLinkGenerator) {
        this.domain = domain;
        this.localCache = localCache;
        this.shortLinkLock = shortLinkLock;
        this.shortLinkStorage = shortLinkStorage;
        this.shortLinkGenerator = shortLinkGenerator;
    }

    /**
     * 获取短链接对应的目标url
     * @param shortLink 短链接
     * @return 目标url
     */
    public String getTargetUrl(String shortLink) {
        return this.getTargetUrl(null, shortLink);
    }

    /**
     * 获取短链接对应的目标url
     * @param prefix 短链接前缀
     * @param shortLink 短链接
     * @return 目标url
     */
    public String getTargetUrl(String prefix, String shortLink) {
        return this.localCache.getTargetUrl(prefix, shortLink);
    }

    /**
     * 生成短链接url
     * @param shortLink 短链接参数
     * @return 短链接
     * @throws UnGetLockException 没获取到锁异常
     */
    public String generateShortLinkUrl(ShortLink shortLink) throws UnGetLockException {
        Assert.notNull(shortLink, "短链接参数不能为空");
        if (StrUtil.isBlank(shortLink.getPrefix())) {
            return String.format("%s/%s", this.domain, shortLink.getShortLink());
        }
        return String.format("%s/%s/%s", this.domain, shortLink.getPrefix(), shortLink.getShortLink());
    }

    /**
     * 生成短链接
     * @param url 目标链接
     * @return 短链接参数
     */
    public ShortLink generate(String url) throws UnGetLockException {
        return this.generate(null, url);
    }

    /**
     * 生成短链接
     * @param prefix 短链接前缀
     * @param url 目标链接
     * @return 短链接参数
     */
    public ShortLink generate(String prefix, String url) throws UnGetLockException {
        if (StrUtil.isBlank(prefix)) prefix = null;

        ShortLinkRequest request = new ShortLinkRequest(prefix, url);

        Integer previousSalt = null;

        for (int i = 0; i < 100000; i++) {  //防止死循环
            ShortLink shortLink = this.shortLinkGenerator.generate(request, previousSalt);
            boolean lock = this.shortLinkLock.lock(shortLink.getShortLink());
            if (!lock) {
                throw  new UnGetLockException("没有获取到锁");
            }
            try {
                ShortLink existsShortLink = this.shortLinkStorage.get(prefix, shortLink.getShortLink());
                if (existsShortLink != null
                        && Objects.equals(existsShortLink.getUrl(), shortLink.getUrl())) {
                    //已经存在
                    return existsShortLink;
                }

                boolean storage = this.shortLinkStorage.storage(shortLink);
                if (storage) {
                    return shortLink;
                }
            } catch (ShortLinkExistsException e) {
                log.info("生成短链接冲突 shortLink -> {}", shortLink);
                previousSalt = shortLink.getSalt();
            } finally {
                this.shortLinkLock.unlock(shortLink.getShortLink());
            }
        }
        throw new RuntimeException("生成短链接冲突，数据量太大");
    }
}
