package live.xu.shortlink.config;

import live.xu.shortlink.cache.remote.RemoteCache;
import live.xu.shortlink.lock.ShortLinkLock;
import live.xu.shortlink.shortlink.MysqlShortLinkStorage;
import live.xu.shortlink.shortlink.RedisRemoteCache;
import live.xu.shortlink.shortlink.RedisShortLinkLock;
import live.xu.shortlink.storage.ShortLinkStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * bean配置
 * Create by xsg at 2023/04/16 09:28.
 */
@Configuration
public class BeanConfig {

    //mysql实现的存储
    @Bean
    public ShortLinkStorage mysqlShortLinkStorage() {
        return new MysqlShortLinkStorage();
    }

    //reids实现的远程缓存
    @Bean
    public RemoteCache redisRemoteCache(ShortLinkStorage shortLinkStorage,
                                        RedisTemplate<String, String> redisTemplate) {
        return new RedisRemoteCache(shortLinkStorage, redisTemplate);
    }

    //redis实现的分布式锁
    @Bean
    public ShortLinkLock redisShortLinkLock() {
        return new RedisShortLinkLock();
    }
}
