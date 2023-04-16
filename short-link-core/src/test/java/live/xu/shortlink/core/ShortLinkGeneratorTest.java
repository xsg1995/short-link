package live.xu.shortlink.core;

import cn.hutool.core.lang.UUID;
import org.junit.Test;

/**
 * ShortLinkGenerator test
 * Create by xsg at 2023/04/15 09:05.
 */
public class ShortLinkGeneratorTest {

    @Test
    public void generate_test() {
        ShortLinkGenerator shortLinkGenerator = new ShortLinkGenerator(new UrlMurmurHashAlgo());

//        Set<String> set = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            String url = UUID.fastUUID().toString();
            ShortLinkRequest shortLinkRequest = new ShortLinkRequest(url, null);
            ShortLink shortLink = shortLinkGenerator.generate(shortLinkRequest, null);

//            if (set.contains(shortLink.getShortLink())) {
//                System.out.println(shortLink);
//            }
//            set.add(shortLink.getShortLink());

            System.out.println(shortLink);
        }

    }
}
