package live.xu.shortlink.cache.remote;

import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.storage.ShortLinkStorage;

/**
 * 直接从存储层获取数据的的远程缓存实现
 * Create by xsg at 2023/04/15 10:15.
 */
public class DirectStorageRemoteCache implements RemoteCache {

    private final ShortLinkStorage shortLinkStorage;

    public DirectStorageRemoteCache(ShortLinkStorage shortLinkStorage) {
        this.shortLinkStorage = shortLinkStorage;
    }

    @Override
    public String getTargetUrl(String prefix, String shortLink) {
        ShortLink shortLinkData = this.shortLinkStorage.get(prefix, shortLink);
        if (shortLinkData == null) {
            return null;
        }
        return shortLinkData.getUrl();
    }
}
