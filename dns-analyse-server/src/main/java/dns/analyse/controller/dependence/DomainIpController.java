package dns.analyse.controller.dependence;

import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainIpPO;
import dns.analyse.model.DomainDependenceVO;
import dns.analyse.model.DomainIpVO;
import dns.analyse.service.IDnsDomainIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/3/10 下午7:13
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/admin/domainIp/api")
public class DomainIpController {
    @Autowired
    IDnsDomainIpService dnsDomainIpService;

    @GetMapping("/list")
    @ResponseBody
    public DomainIpVO getList(DomainIpPO vo, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize){
        List<DomainIpPO> list = dnsDomainIpService.queryPageByCondition(vo,pageNum,pageSize);
        Integer count = 1000000;
        return DomainIpVO.builder()
                .po(list)
                .totalCount(count)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }
}
