package dns.analyse.controller.localOperate;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    @GetMapping("/serverTypeData")
    public Object serverTypeData(){
        Map<String,Integer> serverTypeMap =new HashMap<>();
        serverTypeMap.put("cn",16789);
        serverTypeMap.put("com",594912);
        serverTypeMap.put("org",43758);
        serverTypeMap.put("jp",20882);
        return JSON.toJSON(serverTypeMap);
    }
    @ResponseBody
    @GetMapping("/cdnData")
    public Object cdnData(){
        Map<String,Integer> serverTypeMap =new HashMap<>();
        serverTypeMap.put("cdn",34523);
        serverTypeMap.put("not",1000000-34523);
        return JSON.toJSON(serverTypeMap);
    }
    @ResponseBody
    @GetMapping("/domainNumType")
    public Object domainNumType(){
        Map<String,Integer> serverTypeMap =new HashMap<>();
        serverTypeMap.put("=0",117911);
        serverTypeMap.put("1-3",662357);
        serverTypeMap.put("3-5",805310-662357);
        serverTypeMap.put("5-10",65935);
        serverTypeMap.put("10-15",9105);
        serverTypeMap.put(">15",1739);
        return JSON.toJSON(serverTypeMap);
    }
}
