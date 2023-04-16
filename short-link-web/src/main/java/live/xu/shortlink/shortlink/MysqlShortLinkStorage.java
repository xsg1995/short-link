package live.xu.shortlink.shortlink;

import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.exception.ShortLinkExistsException;
import live.xu.shortlink.storage.ShortLinkStorage;

/**
 * 基于mysql实现的短链接存储
 * Create by xsg at 2023/04/16 09:29.
 */
public class MysqlShortLinkStorage implements ShortLinkStorage {
    @Override
    public boolean storage(ShortLink shortLink) throws ShortLinkExistsException {
        return false;
    }

    @Override
    public ShortLink get(String prefix, String shortLink) {
        return null;
    }
}
