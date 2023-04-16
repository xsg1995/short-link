package live.xu.shortlink.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 短链接
 * Create by xsg at 2023/04/16 14:50.
 */
@Data
@TableName("t_short_link")
public class ShortLinkEntity implements Serializable {

    //生成的短链接
    @TableId
    private String shortLink;
    //生成短链接用到的salt，解决散列冲突
    private int salt;
    //自定义短链接前缀
    private String prefix;
    //原始链接url
    private String url;

}
