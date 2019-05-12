package dns.analyse.controller.dependence;

import dns.analyse.dao.model.DomainCdnPO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainDetailPO;
import dns.analyse.dao.model.DomainIpPO;
import dns.analyse.model.CdnServerInfoVO;
import dns.analyse.model.DomainDependenceVO;
import dns.analyse.service.IDnsDomainCdnService;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IDnsDomainIpService;
import dns.analyse.service.IDomainDetailService;
import dns.analyse.service.tools.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.core.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/23 下午4:07
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/admin/dependence/api")
public class DependenceController {

    private static final Logger LOGGER = LogManager.getLogger(DependenceController.class.getName());

    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;
    @Autowired
    IDnsDomainCdnService dnsDomainCdnService;
    @Autowired
    IDnsDomainIpService dnsDomainIpService;
    @Autowired
    IDomainDetailService domainDetailService;

    @GetMapping("/list")
    @LogAnnotation(title = "依赖信息",action = "获取依赖信息列表")
    @ResponseBody
    public DomainDependenceVO getList(DomainDependencePO vo,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize , String sort){
        LOGGER.info("获取依赖信息列表");
        List<DomainDependencePO> list = dnsDomainDependenceService.queryPageByCondition(vo,pageNum,pageSize);
        Integer count = 1000000;
        if (sort != null){
            list.sort((t1,t2)->t1.getDomainNum()-t2.getDomainNum());
        }
        return DomainDependenceVO.builder()
                .po(list)
                .totalCount(count)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }
    @GetMapping("/cdnServer")
    @ResponseBody
    public List<CdnServerInfoVO> getcdnServer(String domain){
        if(StringUtils.isBlank(domain)){
            return null;
        }
        List<DomainCdnPO> pos = dnsDomainCdnService.queryAllByCondition(DomainCdnPO
                .builder()
                .domain(domain)
                .build());
        List<CdnServerInfoVO> vos = new ArrayList<>();
        pos.forEach(t->{
            String ipDetail = "";
            if(StringUtils.isNotBlank(t.getCdnIp()) && !Objects.equals(" ",t.getCdnIp())){
                List<DomainIpPO> domainIpPOList = dnsDomainIpService.queryAllByCondition(DomainIpPO.builder()
                        .ip(t.getCdnIp()).build());
                ipDetail = Optional.ofNullable(domainIpPOList).map(t1->t1.get(0).getDetail()).orElse("服务器信息未知");
            }

            vos.add(CdnServerInfoVO.builder()
                    .server(t.getCdnServer())
                    .serverIp(Optional.ofNullable(t.getCdnIp()).orElse("ip未获取"))
                    .serverDetail(ipDetail)
                    .build());

        });
        return vos;

    }
    //CDN信息写入缓存

    @GetMapping("/getSubDetail")
    @ResponseBody
    public DomainDetailPO getSubDetail(String domain){
        List<DomainDetailPO> pos = domainDetailService.queryAllByCondition(DomainDetailPO.builder().domain(domain).build());
        if(CollectionUtils.isEmpty(pos)){
            return null;
        }
        DomainDetailPO po = pos.get(0);
        po.setSubnetDetial("");
        return po;
    }

}
