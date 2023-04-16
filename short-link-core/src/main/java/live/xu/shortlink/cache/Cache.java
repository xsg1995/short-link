package live.xu.shortlink.cache;

/**
 * 缓存抽象实现
 * Create by xsg at 2023/04/15 10:21.
 */
public interface Cache {

    /**
     * 获取短链接对应的目标链接
     * @param prefix 短链接前缀
     * @param shortLink 短链接
     * @return 目标链接
     */
    String getTargetUrl(String prefix, String shortLink);
}
