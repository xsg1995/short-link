package live.xu.shortlink.cache.local;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import live.xu.shortlink.cache.remote.RemoteCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基于guava的cache实现
 * Create by xsg at 2023/04/15 09:57.
 */
@Slf4j
public class LoadingCacheLocalCache implements LocalCache {

    //空字符串
    private static final String EMPTY = "";

    private final LoadingCache<LoadingCacheKey, String> loadingCache;

    public LoadingCacheLocalCache(RemoteCache remoteCache) {
        this(1000, 1, remoteCache);
    }

    public LoadingCacheLocalCache(long maxSize, long duration, RemoteCache remoteCache) {
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(Math.max(maxSize, 1))
                .expireAfterAccess(Math.max(duration, 500), TimeUnit.MILLISECONDS)
                .build(new CacheLoader<LoadingCacheKey, String>() {
                    @Override
                    public String load(LoadingCacheKey key) {

                        log.debug("触发远程缓存加载 key -> {}", key);
                        String targetUrl = remoteCache.getTargetUrl(key.prefix, key.shortLink);
                        if (targetUrl == null) return EMPTY;

                        return targetUrl;
                    }
                });
    }

    @Override
    public String getTargetUrl(String shortLink) {
        return this.getTargetUrl(null, shortLink);
    }

    @Override
    public String getTargetUrl(String prefix, String shortLink) {
        String targetUrl = this.loadingCache.getUnchecked(new LoadingCacheKey(prefix, shortLink));
        if (EMPTY.equals(targetUrl)) return null;  //空字符返回null
        return targetUrl;
    }

    //本地缓存key
    static class LoadingCacheKey {
        private String prefix;  //前缀
        private String shortLink; //短链接

        public LoadingCacheKey(String prefix, String shortLink) {
            this.prefix = prefix;
            this.shortLink = shortLink;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getShortLink() {
            return shortLink;
        }

        public void setShortLink(String shortLink) {
            this.shortLink = shortLink;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LoadingCacheKey that = (LoadingCacheKey) o;
            return Objects.equals(prefix, that.prefix) && Objects.equals(shortLink, that.shortLink);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prefix, shortLink);
        }

        @Override
        public String toString() {
            return "LoadingCacheKey{" +
                    "prefix='" + prefix + '\'' +
                    ", shortLink='" + shortLink + '\'' +
                    '}';
        }
    }
}
