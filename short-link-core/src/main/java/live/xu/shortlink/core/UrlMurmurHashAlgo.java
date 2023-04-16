package live.xu.shortlink.core;

import cn.hutool.core.lang.hash.MurmurHash;

/**
 * 基于Murmur3实现的hash算法
 * Create by xsg at 2023/04/15 08:49.
 */
public class UrlMurmurHashAlgo implements UrlHashAlgo {
    @Override
    public long hash(String url) {
        return Math.abs(MurmurHash.hash32(url));
    }
}
