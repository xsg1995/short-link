package live.xu.shortlink.storage;

import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.exception.ShortLinkExistsException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存实现的存储
 * Create by xsg at 2023/04/15 10:25.
 */
public class MemoryShortLinkStorage implements ShortLinkStorage {

    private final Map<String, ShortLink> map = new ConcurrentHashMap<>();

    @Override
    public boolean storage(ShortLink shortLink) throws ShortLinkExistsException {
        if (shortLink == null) return false;

        ShortLink absent = this.map.putIfAbsent(shortLink.getShortLink(), shortLink);
        if (absent != null && Objects.equals(absent.getShortLink(), shortLink.getShortLink())) {
            //短链接冲突
            throw new ShortLinkExistsException();
        }
        return true;
    }

    @Override
    public ShortLink get(String prefix, String shortLink) {
        ShortLink shortLinkData = this.map.get(shortLink);
        if (shortLinkData == null) return null;

        if (Objects.equals(shortLinkData.getPrefix(), prefix)) return shortLinkData;

        return null;
    }
}
