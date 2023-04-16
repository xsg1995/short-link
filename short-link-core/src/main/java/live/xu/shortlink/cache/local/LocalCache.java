package live.xu.shortlink.cache.local;

import live.xu.shortlink.cache.Cache;

/**
 * 本地缓存
 * Create by xsg at 2023/04/15 09:38.
 */
public interface LocalCache extends Cache {

    /**
     * 获取短链接对应的目标链接
     * @param shortLink 短链接
     * @return 目标链接
     */
    String getTargetUrl(String shortLink);

}
