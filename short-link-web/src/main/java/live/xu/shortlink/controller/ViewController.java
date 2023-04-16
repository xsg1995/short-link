package live.xu.shortlink.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 视图
 * Create by xsg at 2023/04/15 21:04.
 */
@Slf4j
@Controller
public class ViewController {

    @GetMapping("view.htm")
    public String view() {
        return "view";
    }
}
