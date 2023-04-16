package live.xu.shortlink.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 短链接生成参数
 * Create by xsg at 2023/04/15 14:55.
 */
@Getter
@Setter
public class ShortLinkGenerateDTO {
    //自定义短链接前缀
    private String prefix;
    @NotBlank(message = "url链接不能为空")
    private String url;
}
