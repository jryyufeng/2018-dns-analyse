package dns.analyse.controller.localOperate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/21 下午2:37
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/LocalOperate")
public class IndexController {
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("envs", "test");
        return "index";
    }
}
