package live.xu.shortlink.utils;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;

/**
 * url工具类
 * Create by xsg at 2023/04/15 16:06.
 */
public class UrlUtils {

    //验证url是否正常的http url
    public static boolean valid(String url) {
        if (StrUtil.isBlank(url)) return false;

        UrlBuilder urlBuilder = UrlBuilder.of(url);

        String scheme = urlBuilder.getScheme();
        if (StrUtil.isBlank(scheme)) return false;

        if (!scheme.equalsIgnoreCase("https")
            && !scheme.equalsIgnoreCase("http")) return false;

        String host = urlBuilder.getHost();
        if (StrUtil.isBlank(host)) return false;

        return true;
    }




}
