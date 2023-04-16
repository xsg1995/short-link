package live.xu.shortlink.exception;

/**
 * 没有获取到锁异常
 * Create by xsg at 2023/04/15 09:43.
 */
public class UnGetLockException extends Exception {

    public UnGetLockException() {
    }

    public UnGetLockException(String message) {
        super(message);
    }
}
