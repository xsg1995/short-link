package live.xu.shortlink.starter;

import live.xu.shortlink.ShortLinkExecutor;
import live.xu.shortlink.cache.local.LoadingCacheLocalCache;
import live.xu.shortlink.cache.local.LocalCache;
import live.xu.shortlink.cache.remote.DirectStorageRemoteCache;
import live.xu.shortlink.cache.remote.RemoteCache;
import live.xu.shortlink.lock.DefaultShortLinkLock;
import live.xu.shortlink.lock.ShortLinkLock;
import live.xu.shortlink.storage.MemoryShortLinkStorage;
import live.xu.shortlink.storage.ShortLinkStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短链接 spring boot 自定配置
 * Create by xsg at 2023/04/15 14:28.
 */
@Configuration
@ConditionalOnClass({ShortLinkExecutor.class, ShortLinkProperties.class})
@EnableConfigurationProperties({ShortLinkProperties.class})
public class ShortLinkAutoConfiguration {

    //短链接执行入口
    @Bean
    @ConditionalOnMissingBean({ShortLinkExecutor.class})
    public ShortLinkExecutor shortLinkExecutor(ShortLinkProperties shortLinkProperties,
                                               LocalCache localCache,
                                               ShortLinkLock shortLinkLock,
                                               ShortLinkStorage shortLinkStorage) {
        return new ShortLinkExecutor(shortLinkProperties.getDomain(), localCache, shortLinkLock, shortLinkStorage);
    }

    //本地缓存，默认使用guava LoadingCache 实现
    @Bean
    @ConditionalOnMissingBean({LocalCache.class})
    public LocalCache localCache(ShortLinkProperties shortLinkProperties, RemoteCache remoteCache) {
        return new LoadingCacheLocalCache(
                shortLinkProperties.getLocalCacheMaxSize(),
                shortLinkProperties.getLocalCacheExpire(),
                remoteCache);
    }

    //远程缓存，默认直接从数据库读取
    @Bean
    @ConditionalOnMissingBean({RemoteCache.class})
    public RemoteCache remoteCache(ShortLinkStorage shortLinkStorage) {
        return new DirectStorageRemoteCache(shortLinkStorage);
    }

    //数据存储，默认存储在内存中
    @Bean
    @ConditionalOnMissingBean({ShortLinkStorage.class})
    public ShortLinkStorage shortLinkStorage() {
        return new MemoryShortLinkStorage();
    }

    //自定义锁，默认本地锁实现
    @Bean
    @ConditionalOnMissingBean({ShortLinkLock.class})
    public ShortLinkLock shortLinkLock() {
        return new DefaultShortLinkLock();
    }
}
