package live.xu.shortlink.controller.advice;

import live.xu.shortlink.resp.ResponseResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常拦截
 * Create by xsg at 2023/04/15 16:18.
 */
@Slf4j
@RestControllerAdvice
public class ShotLinkControllerAdvice {

    /**
     * 校验错误拦截处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResp<String> validationBodyException(MethodArgumentNotValidException exception) {
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        List<String> errors = new ArrayList<>();
        for (ObjectError allError : allErrors) {
            errors.add(allError.getDefaultMessage());
        }

        return ResponseResp.fail(errors.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResp<String> illegalArgumentException(IllegalArgumentException illegalArgumentException) {
        log.error("参数异常", illegalArgumentException);
        return ResponseResp.fail(illegalArgumentException.getMessage());
    }
}
