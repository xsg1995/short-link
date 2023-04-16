package live.xu.shortlink.core;

import java.util.Objects;

/**
 * 生成的短链接信息
 * Create by xsg at 2023/04/15 08:31.
 */
public class ShortLink {
    //原始链接url
    private final String url;
    //自定义短链接前缀
    private final String prefix;
    //生成的短链接
    private final String shortLink;
    //生成短链接用到的salt，解决散列冲突
    private final int salt;

    public ShortLink(String url, String prefix,  String shortLink, int salt) {
        this.prefix = prefix;
        this.url = url;
        this.shortLink = shortLink;
        this.salt = salt;
    }

    public String getUrl() {
        return url;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getShortLink() {
        return shortLink;
    }

    public int getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortLink shortLink1 = (ShortLink) o;
        return salt == shortLink1.salt && Objects.equals(url, shortLink1.url) && Objects.equals(prefix, shortLink1.prefix) && Objects.equals(shortLink, shortLink1.shortLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, prefix, shortLink, salt);
    }

    @Override
    public String toString() {
        return "ShortLink{" +
                "url='" + url + '\'' +
                ", prefix='" + prefix + '\'' +
                ", shortLink='" + shortLink + '\'' +
                ", salt=" + salt +
                '}';
    }
}
