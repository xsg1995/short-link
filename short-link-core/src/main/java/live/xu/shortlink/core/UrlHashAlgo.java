package live.xu.shortlink.core;

/**
 * url生成hash值的算法
 * Create by xsg at 2023/04/15 08:47.
 */
public interface UrlHashAlgo {

    /**
     * 根据url生成hash值
     * @param url url
     * @return hash值，需要返回正数
     */
    long hash(String url);
}
