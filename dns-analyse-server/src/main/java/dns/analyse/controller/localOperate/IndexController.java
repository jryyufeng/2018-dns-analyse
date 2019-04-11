package dns.analyse.controller.localOperate;

import com.alibaba.fastjson.JSON;
import dns.analyse.service.IDnsDomainCdnService;
import dns.analyse.service.IDnsDomainDependenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/21 下午2:37
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/LocalOperate")
public class IndexController {
    @Autowired
    IDnsDomainCdnService dnsDomainCdnService;
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("envs", "test");
        return "index";
    }
    @ResponseBody
    @GetMapping("/serverTypeData")
    public Object serverTypeData(){
        Map<String,Integer> serverTypeMap =new HashMap<>();
        int cnNum = dnsDomainDependenceService.getDOMAIN_TYPE_NUM("cn");
        int comNum = dnsDomainDependenceService.getDOMAIN_TYPE_NUM("com");
        int orgNum = dnsDomainDependenceService.getDOMAIN_TYPE_NUM("org");
        int jpNum = dnsDomainDependenceService.getDOMAIN_TYPE_NUM("jp");
        serverTypeMap.put("cn",cnNum);
        serverTypeMap.put("com",comNum);
        serverTypeMap.put("org",orgNum);
        serverTypeMap.put("jp",jpNum);
        serverTypeMap.put("other",1000000-(cnNum+comNum+orgNum+jpNum));
        return JSON.toJSON(serverTypeMap);
    }
    @ResponseBody
    @GetMapping("/cdnData")
    public Object cdnData(){
        Map<String,Integer> serverTypeMap =new HashMap<>();
        Integer cdnNum = dnsDomainCdnService.getCDN_NUM("domain");
        serverTypeMap.put("cdn",cdnNum );
        serverTypeMap.put("not",1000000-cdnNum);
        return JSON.toJSON(serverTypeMap);
    }
    @ResponseBody
    @GetMapping("/domainNumType")
    public Object domainNumType(){
        Map<String,Integer> serverTypeMap =new HashMap<>();
        serverTypeMap.put("=0",dnsDomainDependenceService.getDOMAIN_NUMTYPE("=0"));
        serverTypeMap.put("1-3",dnsDomainDependenceService.getDOMAIN_NUMTYPE("1-3"));
        serverTypeMap.put("3-5",dnsDomainDependenceService.getDOMAIN_NUMTYPE("3-5"));
        serverTypeMap.put("5-10",dnsDomainDependenceService.getDOMAIN_NUMTYPE("5-10"));
        serverTypeMap.put("10-15",dnsDomainDependenceService.getDOMAIN_NUMTYPE("10-15"));
        serverTypeMap.put(">15",dnsDomainDependenceService.getDOMAIN_NUMTYPE(">15"));
        return JSON.toJSON(serverTypeMap);
    }
}
