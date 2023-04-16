package live.xu.shortlink.core;

/**
 * 生成短链接参数
 * Create by xsg at 2023/04/15 08:33.
 */
public class ShortLinkRequest {
    //自定义短链接前缀
    private String prefix;
    //原始链接url
    private final String url;

    public ShortLinkRequest(String url) {
        this.url = url;
    }

    public ShortLinkRequest(String prefix, String url) {
        this.prefix = prefix;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return "ShortLinkRequest{" +
                "url='" + url + '\'' +
                ", prefix='" + prefix + '\'' +
                '}';
    }
}
