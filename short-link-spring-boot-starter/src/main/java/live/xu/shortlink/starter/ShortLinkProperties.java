package live.xu.shortlink.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短链接配置
 * Create by xsg at 2023/04/15 14:41.
 */
@ConfigurationProperties(prefix = "short.link")
public class ShortLinkProperties {
    //短链接域名
    private String domain;
    //本地缓存最大数量
    private long localCacheMaxSize;
    //本地缓存过期时间，单位：毫秒
    private long localCacheExpire;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getLocalCacheMaxSize() {
        return localCacheMaxSize;
    }

    public void setLocalCacheMaxSize(long localCacheMaxSize) {
        this.localCacheMaxSize = localCacheMaxSize;
    }

    public long getLocalCacheExpire() {
        return localCacheExpire;
    }

    public void setLocalCacheExpire(long localCacheExpire) {
        this.localCacheExpire = localCacheExpire;
    }
}
