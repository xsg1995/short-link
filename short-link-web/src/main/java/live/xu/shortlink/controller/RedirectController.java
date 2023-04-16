package live.xu.shortlink.controller;

import cn.hutool.core.net.url.UrlBuilder;
import live.xu.shortlink.ShortLinkExecutor;
import live.xu.shortlink.exception.ParamsErrorException;
import live.xu.shortlink.utils.UrlUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 短链接跳转
 * Create by xsg at 2023/04/15 15:40.
 */
@Slf4j
@Controller
@AllArgsConstructor
public class RedirectController {

    private final ShortLinkExecutor shortLinkExecutor;

    //短链接跳转
    @GetMapping("/{shortLink}")
    public String redirect(@PathVariable("shortLink") String shortLink) {
        String targetUrl = this.shortLinkExecutor.getTargetUrl(null, shortLink);
        log.info("短链接对应的目标链接 shortLink -> {} targetUrl -> {}", shortLink, targetUrl);

        //验证url
        boolean valid = UrlUtils.valid(targetUrl);
        if (!valid) throw new ParamsErrorException("跳转目标链接异常 " + targetUrl);

        return "redirect:" + UrlBuilder.of(targetUrl).build();
    }

    //短链接跳转
    @GetMapping("/{prefix:^[A-Za-z]{1,3}}/{shortLink}")
    public String redirectWithPrefix(
            @PathVariable("prefix") String prefix,
            @PathVariable("shortLink") String shortLink) {
        String targetUrl = this.shortLinkExecutor.getTargetUrl(prefix, shortLink);
        log.info("短链接对应的目标链接 prefix -> {} shortLink -> {} targetUrl -> {}", prefix, shortLink, targetUrl);

        //验证url
        boolean valid = UrlUtils.valid(targetUrl);
        if (!valid) throw new ParamsErrorException("跳转目标链接异常 " + targetUrl);

        return "redirect:" + UrlBuilder.of(targetUrl).build();
    }
}
