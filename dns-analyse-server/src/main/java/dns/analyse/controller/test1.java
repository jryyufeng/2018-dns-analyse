package dns.analyse.controller;

import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.service.IDnsDomainDependenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

import java.util.List;


/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/19 下午4:11
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/TestDns")
public class test1 {
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;
    @ResponseBody
    @GetMapping("/queryDependence")
    public Object commonQuery() {
        List<DomainDependencePO> list = dnsDomainDependenceService.queryByIds(Arrays.asList(1,2));
        return list;
    }
//    可能还需要@Component
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void testLog () {
//        System.out.println("123");
//    }
}
