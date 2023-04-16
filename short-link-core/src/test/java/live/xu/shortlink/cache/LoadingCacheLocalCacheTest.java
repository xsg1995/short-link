package live.xu.shortlink.cache;

import cn.hutool.core.lang.UUID;
import live.xu.shortlink.cache.local.LoadingCacheLocalCache;
import live.xu.shortlink.cache.local.LocalCache;
import live.xu.shortlink.cache.remote.DirectStorageRemoteCache;
import live.xu.shortlink.cache.remote.RemoteCache;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.core.ShortLinkGenerator;
import live.xu.shortlink.core.ShortLinkRequest;
import live.xu.shortlink.core.UrlMurmurHashAlgo;
import live.xu.shortlink.exception.ShortLinkExistsException;
import live.xu.shortlink.storage.MemoryShortLinkStorage;
import live.xu.shortlink.storage.ShortLinkStorage;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * LoadingCacheLocalCache test
 * Create by xsg at 2023/04/15 10:45.
 */
public class LoadingCacheLocalCacheTest {

    @Test
    public void get_test() {
        ShortLinkGenerator shortLinkGenerator = new ShortLinkGenerator(new UrlMurmurHashAlgo());

        ShortLinkStorage shortLinkStorage = new MemoryShortLinkStorage();
        RemoteCache remoteCache = new DirectStorageRemoteCache(shortLinkStorage);
        LocalCache localCache = new LoadingCacheLocalCache(5, 1, remoteCache);

        String prefix = "r";
        Map<String, String> shortLinkMap = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            String url = UUID.fastUUID().toString();
            ShortLinkRequest shortLinkRequest = new ShortLinkRequest(url, prefix);
            ShortLink shortLink = shortLinkGenerator.generate(shortLinkRequest, null);
            try {
                shortLinkStorage.storage(shortLink);
                shortLinkMap.put(shortLink.getShortLink(), shortLink.getUrl());
            } catch (ShortLinkExistsException ignored) {}
        }

        for (Map.Entry<String, String> entry : shortLinkMap.entrySet()) {
            String shortLink = entry.getKey();
            String targetUrl = localCache.getTargetUrl(prefix, shortLink);
            Assert.assertEquals(entry.getValue(), targetUrl);
        }

        String targetUrl = localCache.getTargetUrl(prefix, "test");
        Assert.assertNull(targetUrl);

        targetUrl = localCache.getTargetUrl("test");
        Assert.assertNull(targetUrl);

    }
}
