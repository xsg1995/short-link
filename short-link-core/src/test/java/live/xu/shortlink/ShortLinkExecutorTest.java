package live.xu.shortlink;

import live.xu.shortlink.cache.remote.DirectStorageRemoteCache;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.exception.UnGetLockException;
import live.xu.shortlink.lock.DefaultShortLinkLock;
import live.xu.shortlink.storage.MemoryShortLinkStorage;
import live.xu.shortlink.storage.ShortLinkStorage;
import org.junit.Test;

/**
 * Create by xsg at 2023/04/15 12:38.
 */
public class ShortLinkExecutorTest {

    @Test
    public void test() throws UnGetLockException {
        ShortLinkStorage shortLinkStorage = new MemoryShortLinkStorage();
        ShortLinkExecutor shortLinkExecutor = new ShortLinkExecutor(null, new DirectStorageRemoteCache(shortLinkStorage), new DefaultShortLinkLock(), shortLinkStorage);

        String prefix = "r";
        String url = "https://www.baidu.com";
        ShortLink shortLink = shortLinkExecutor.generate(prefix, url);
        System.out.println("shortLink:" + shortLink);


        shortLink = shortLinkExecutor.generate(prefix, url);
        System.out.println("shortLink:" + shortLink);

        shortLink = shortLinkExecutor.generate("p", url);
        System.out.println("shortLink:" + shortLink);
    }
}
