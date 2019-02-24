package dns.analyse.controller.localOperate;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/24 下午3:28
 * Description:
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/api/monitor")
public class ChenkApi {
    @ResponseBody
    @GetMapping("/alive")
    public Object checkAlive(){
        return true;
    }
}
