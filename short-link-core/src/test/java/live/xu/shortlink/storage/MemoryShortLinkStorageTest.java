package live.xu.shortlink.storage;

import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.exception.ShortLinkExistsException;
import org.junit.Assert;
import org.junit.Test;

/**
 * ShortLinkStorage test
 * Create by xsg at 2023/04/15 10:30.
 */
public class MemoryShortLinkStorageTest {

    private final ShortLinkStorage shortLinkStorage = new MemoryShortLinkStorage();

    @Test
    public void test() {
        String url = "https://www.baidu.com";
        String prefix = "r";
        String shortLink = "Ijndur9d";
        ShortLink shortLink1 = new ShortLink(url, prefix, shortLink, 0);
        try {
            this.shortLinkStorage.storage(shortLink1);
        } catch (ShortLinkExistsException e) {
            Assert.assertNull(e);
        }

        try {
            this.shortLinkStorage.storage(shortLink1);
        } catch (ShortLinkExistsException e) {
            Assert.assertNotNull(e);
        }

        ShortLink actualShortLink = this.shortLinkStorage.get(prefix, shortLink);
        Assert.assertEquals(shortLink1, actualShortLink);

        actualShortLink = this.shortLinkStorage.get(null, shortLink);
        Assert.assertNull(actualShortLink);

        actualShortLink = this.shortLinkStorage.get("p", shortLink);
        Assert.assertNull(actualShortLink);
    }

}
