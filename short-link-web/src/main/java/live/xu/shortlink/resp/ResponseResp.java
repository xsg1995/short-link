package live.xu.shortlink.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回对象
 * Create by xsg at 2023/04/15 15:01.
 */
@Getter
@Setter
public class ResponseResp<T> {
    //成功状态码
    private static final int SUCCESS_CODE = 1;
    //异常状态码
    private static final int ERROR_CODE = 0;

    private Integer code;
    private String msg;
    private T data;

    public ResponseResp(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //成功
    public static <T> ResponseResp<T> success(T data) {
        return new ResponseResp<>(SUCCESS_CODE, null, data);
    }

    //失败
    public static <T> ResponseResp<T> fail(String msg) {
        return new ResponseResp<>(ERROR_CODE, msg, null);
    }
}
