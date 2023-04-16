package live.xu.shortlink.shortlink;

import cn.hutool.core.util.StrUtil;
import live.xu.shortlink.cache.remote.RemoteCache;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.storage.ShortLinkStorage;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis实现的远程缓存
 * Create by xsg at 2023/04/16 09:27.
 */
@AllArgsConstructor
public class RedisRemoteCache implements RemoteCache {

    private final ShortLinkStorage shortLinkStorage;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getTargetUrl(String prefix, String shortLink) {
        String key = this.getKey(prefix, shortLink);
        String targetUrl = this.redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(targetUrl)) return targetUrl;

        ShortLink shortLinkData = this.shortLinkStorage.get(prefix, shortLink);
        if (shortLinkData == null) {
            this.redisTemplate.opsForValue().set(key, "", 10, TimeUnit.SECONDS);
            return null;
        }
        targetUrl = shortLinkData.getUrl();
        this.redisTemplate.opsForValue().set(key, targetUrl, 1, TimeUnit.HOURS);
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
