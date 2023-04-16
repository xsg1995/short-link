package live.xu.shortlink.storage;

import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.exception.ShortLinkExistsException;

/**
 * 短链接存储
 * Create by xsg at 2023/04/15 09:42.
 */
public interface ShortLinkStorage {

    /**
     * 存储短链接
     * @param shortLink 短链接参数
     * @return 是否存储成功
     * @throws ShortLinkExistsException 短链接已经存在异常
     */
    boolean storage(ShortLink shortLink) throws ShortLinkExistsException;

    /**
     * 获取短链接数据
     * @param prefix 短链接前缀
     * @param shortLink 短链接
     * @return 短链接数据
     */
    ShortLink get(String prefix, String shortLink);
}
