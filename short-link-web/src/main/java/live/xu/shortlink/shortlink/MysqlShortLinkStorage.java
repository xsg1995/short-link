package live.xu.shortlink.shortlink;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.entity.ShortLinkEntity;
import live.xu.shortlink.exception.ShortLinkExistsException;
import live.xu.shortlink.mapper.ShortLinkMapper;
import live.xu.shortlink.storage.ShortLinkStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 基于mysql实现的短链接存储
 * Create by xsg at 2023/04/16 09:29.
 */
@Slf4j
public class MysqlShortLinkStorage implements ShortLinkStorage {

    @Autowired
    private ShortLinkMapper shortLinkMapper;

    @Override
    public boolean storage(ShortLink shortLink) throws ShortLinkExistsException {
        try {
            ShortLinkEntity shortLinkEntity = BeanUtil.toBean(shortLink, ShortLinkEntity.class);
            return this.shortLinkMapper.insert(shortLinkEntity) == 1;
        } catch (DuplicateKeyException e) {
            throw new ShortLinkExistsException();  //短链接冲突
        }
    }

    @Override
    public ShortLink get(String prefix, String shortLink) {
        LambdaQueryWrapper<ShortLinkEntity> queryWrapper = Wrappers.lambdaQuery(ShortLinkEntity.class);
        queryWrapper.eq(StrUtil.isNotBlank(prefix), ShortLinkEntity::getPrefix, prefix);
        queryWrapper.eq(ShortLinkEntity::getShortLink, shortLink);
        ShortLinkEntity shortLinkEntity = this.shortLinkMapper.selectOne(queryWrapper);
        if (shortLinkEntity == null) return null;

        return new ShortLink(shortLinkEntity.getUrl(), shortLinkEntity.getPrefix(), shortLinkEntity.getShortLink(), shortLinkEntity.getSalt());
    }
}
