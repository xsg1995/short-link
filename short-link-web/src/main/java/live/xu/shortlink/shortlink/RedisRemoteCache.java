package live.xu.shortlink.shortlink;

import cn.hutool.core.util.StrUtil;
import live.xu.shortlink.cache.remote.RemoteCache;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.storage.ShortLinkStorage;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;

import java.util.concurrent.TimeUnit;

/**
 * redis实现的远程缓存
 * Create by xsg at 2023/04/16 09:27.
 */
@AllArgsConstructor
public class RedisRemoteCache implements RemoteCache {

    //空字符
    private final static String EMPTY_STR = "";
    //远程缓存
    private final ShortLinkStorage shortLinkStorage;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getTargetUrl(String prefix, String shortLink) {
        String key = this.getKey(prefix, shortLink);
        String targetUrl = this.redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(targetUrl)) return targetUrl;
        if (EMPTY_STR.equals(targetUrl)) return null;  //空字符，链接不存在

        ShortLink shortLinkData = this.shortLinkStorage.get(prefix, shortLink);
        if (shortLinkData == null) {
            this.redisTemplate.opsForValue().set(key, EMPTY_STR, 5, TimeUnit.MINUTES);  // 链接不存在，放入空字符5分钟
            return null;
        }
        targetUrl = shortLinkData.getUrl();
        this.redisTemplate.opsForValue().set(key, targetUrl, 1, TimeUnit.HOURS);  // 缓存1个小时
        return targetUrl;
    }

    //获取redis的key
    private String getKey(String prefix, String shortLink) {
        if (StrUtil.isNotBlank(prefix)) {
            return "short:link:" + prefix + ":" + shortLink;
        } else {
            return "short:link::" + shortLink;
        }

    }
}
