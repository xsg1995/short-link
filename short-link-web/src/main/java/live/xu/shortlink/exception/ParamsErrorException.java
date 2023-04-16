package live.xu.shortlink.exception;

/**
 * 参数错误异常
 * Create by xsg at 2023/04/16 18:34.
 */
public class ParamsErrorException extends RuntimeException {

    public ParamsErrorException(String message) {
        super(message);
    }
}
