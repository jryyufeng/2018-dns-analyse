package dns.analyse.controller.analyseSystemApi;

import dns.analyse.dao.model.DomainDetailPO;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IDomainDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/6/5
 * @time: 14:42
 */
@Slf4j
@Controller
@RequestMapping("/handle/system/api")
public class DomainApi {
    @Autowired
    private IDomainDetailService domainDetailService;
    @Autowired
    private IDnsDomainDependenceService dnsDomainDependenceService;

    /**
     * 分析top100w域名子域详情，及跨域情况，返回结果
     */
    @GetMapping("/queryByDomain")
    @ResponseBody
    public DomainDetailPO queryByDomain(String domain){
        List<DomainDetailPO> pos = domainDetailService.queryAllByCondition(DomainDetailPO.builder().domain(domain).build());
        if(CollectionUtils.isEmpty(pos)){
            return null;
        }
        return pos.get(0);
    }
    /**
     * 更新top100w，域名详情
     */
    @PostMapping("/updateByDomain")
    @ResponseBody
    public boolean updateByDomain(String domain){
        return domainDetailService.detailHadnlerByDomain(domain);
    }

    /**
     * 更新顶级域类型
     */
    @PostMapping("/updateTypeByDomain")
    @ResponseBody()
    public boolean updateTypeByDomain(String domain){
        return dnsDomainDependenceService.setDomainTypeByDomain(domain);
    }
    /**
     * 刷数据，更新域名详情
     */
    @PostMapping("/updateDetail")
    @ResponseBody
    public void updateDetail(Integer start,Integer end){
        domainDetailService.detailHandler(start,end);
    }
}
