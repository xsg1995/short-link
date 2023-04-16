package live.xu.shortlink.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import live.xu.shortlink.entity.ShortLinkEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短链接mapper
 * Create by xsg at 2023/04/16 14:49.
 */
@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLinkEntity> {
}
