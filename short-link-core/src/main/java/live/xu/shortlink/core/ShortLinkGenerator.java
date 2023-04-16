package live.xu.shortlink.core;

/**
 * 短链接生成核心逻辑
 * Create by xsg at 2023/04/15 08:30.
 */
public class ShortLinkGenerator {
    //生成短链接包含的字符
    private static final char[] SHORT_LINK_CHAR_ARR =
            {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                    'Y', 'Z'
            };

    private final UrlHashAlgo urlHashAlgo;

    public ShortLinkGenerator(UrlHashAlgo urlHashAlgo) {
        this.urlHashAlgo = urlHashAlgo;
    }

    /**
     * 根据请求参数生成短链接
     * @param request 请求参数，封装要生成短链接的目标url
     * @param previousSalt 上一个生成短链接的盐，当短链接发生冲突时需要使用该参数
     * @return 生成的短链接信息
     */
    public ShortLink generate(ShortLinkRequest request, Integer previousSalt) {
        //拼接目标url
        int targetSalt = previousSalt == null ? 0 : previousSalt + 1;
        String targetUrl = this.generateTargetUrl(request, previousSalt);

        //使用hash算法生成hash值
        long targetUrlHash = this.urlHashAlgo.hash(targetUrl);

        //根据hash值生成短链接
        String shortLink = this.generateShortLink(targetUrlHash);

        return new ShortLink(request.getUrl(), request.getPrefix(), shortLink, targetSalt);
    }

    //根据请求参数生成目标url
    private String generateTargetUrl(ShortLinkRequest request, Integer previousSalt) {
        String url = request.getUrl();
        String prefix = request.getPrefix();

        return prefix + url + previousSalt;
    }

    //根据hash值生成短链接的值
    private String generateShortLink(long hash) {
        StringBuilder shortLinkBuilder = new StringBuilder();
        int radix = SHORT_LINK_CHAR_ARR.length;
        while (hash > 0) {
            int mod = (int) (hash % radix);
            shortLinkBuilder.append(SHORT_LINK_CHAR_ARR[mod]);

            hash /= radix;
        }
        return shortLinkBuilder.toString();
    }
}
