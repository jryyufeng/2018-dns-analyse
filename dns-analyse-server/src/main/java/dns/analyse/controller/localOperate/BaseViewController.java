package dns.analyse.controller.localOperate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/23 下午3:19
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/admin/view/{moudle}")
public class BaseViewController {
    @GetMapping("/{feature}")
    public String redict(@PathVariable String moudle, @PathVariable String feature) {
        return moudle + "/" + feature;
    }
}
