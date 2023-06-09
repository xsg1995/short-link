package live.xu.shortlink.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import live.xu.shortlink.ShortLinkExecutor;
import live.xu.shortlink.core.ShortLink;
import live.xu.shortlink.dto.ShortLinkGenerateDTO;
import live.xu.shortlink.exception.UnGetLockException;
import live.xu.shortlink.exception.ParamsErrorException;
import live.xu.shortlink.resp.ResponseResp;
import live.xu.shortlink.utils.UrlUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 短链接controller
 * Create by xsg at 2023/04/15 14:53.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("shortlink")
public class ShortLinkController {

    private final ShortLinkExecutor shortLinkExecutor;

    //生成短链接
    @PostMapping("generate")
    public ResponseResp<ShortLink> generate(@RequestBody @Validated ShortLinkGenerateDTO shortLinkGenerateDTO) {
        try {
            //验证url是否正确
            this.validUrlParams(shortLinkGenerateDTO);
            ShortLink shortLink = this.shortLinkExecutor.generate(shortLinkGenerateDTO.getPrefix(), shortLinkGenerateDTO.getUrl().trim());
            return ResponseResp.success(shortLink);
        } catch (UnGetLockException e) {
            log.error("生成短链接异常，请重试", e);
        }
        return ResponseResp.fail("生成短链接异常，请重试");
    }

    //生成短链接
    @PostMapping("generateShortLinkUrl")
    public ResponseResp<String> generateShortLinkUrl(@RequestBody @Validated ShortLinkGenerateDTO shortLinkGenerateDTO) {
        try {
            //验证url是否正确
            this.validUrlParams(shortLinkGenerateDTO);
            ShortLink shortLink = this.shortLinkExecutor.generate(shortLinkGenerateDTO.getPrefix(), shortLinkGenerateDTO.getUrl().trim());
            return ResponseResp.success(this.shortLinkExecutor.generateShortLinkUrl(shortLink));
        } catch (UnGetLockException e) {
            log.error("生成短链接异常，请重试", e);
        }
        return ResponseResp.fail("生成短链接异常，请重试");
    }

    //获取目标链接
    @GetMapping("get")
    public ResponseResp<String> get(@RequestParam(value = "prefix", required = false) String prefix,
                                    @RequestParam("shortLink") String shortLink) {
        String targetUrl = this.shortLinkExecutor.getTargetUrl(prefix, shortLink);
        return ResponseResp.success(targetUrl);
    }

    //验证提交的参数是否正确
    private void validUrlParams(ShortLinkGenerateDTO shortLinkGenerateDTO) {
        boolean valid = UrlUtils.valid(shortLinkGenerateDTO.getUrl());
        if (!valid) throw new ParamsErrorException("url地址格式错误 " + shortLinkGenerateDTO.getUrl());

        String prefix = shortLinkGenerateDTO.getPrefix();
        if (StrUtil.isNotBlank(prefix) && prefix.trim().length() > 3) {
            throw new ParamsErrorException("自定义前缀长度不能超过3");
        }
        if (StrUtil.isNotBlank(prefix)) {
            char[] chars = prefix.toCharArray();
            for (char c : chars) {
                if (!CharUtil.isLetter(c)) {
                    throw new ParamsErrorException("自定义前缀只能包含字母A~Z或a~z");
                }
            }
        }

    }
}
