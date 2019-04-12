package dns.analyse.controller.dependence;

import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainIpPO;
import dns.analyse.model.DomainDependenceVO;
import dns.analyse.model.DomainIpVO;
import dns.analyse.model.IpRegionVO;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IDnsDomainIpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;

    @GetMapping("/list")
    @ResponseBody
    public DomainIpVO getList(DomainIpPO vo, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize){
        if(StringUtils.isNotBlank(vo.getDomain())){
            List<DomainDependencePO> listDomain = dnsDomainDependenceService.queryAllByCondition(
                    DomainDependencePO.builder()
                            .domain(vo.getDomain())
                            .crossDomain(2)
                            .build()
            );
            if(CollectionUtils.isNotEmpty(listDomain)){
                String xml = listDomain.get(0).getDomainIp();
                List<String> ips = getAllIp(xml);
                if(CollectionUtils.isEmpty(ips)){
                    return null;
                }
                List<DomainIpPO> pos = new ArrayList<>();
                //获取ip信息
                ips.forEach(t->{
                    String ipDetail = "";
                    List<DomainIpPO> domainIpPOList = dnsDomainIpService.queryAllByCondition(DomainIpPO.builder()
                            .ip(t).build());
                    ipDetail = Optional.ofNullable(domainIpPOList).map(t1->t1.get(0).getDetail()).orElse("服务器信息未知");
                    //String region = Optional.ofNullable(domainIpPOList).map(t1->t1.get(0).getRegion()).orElse("服务器信息未知");
                    pos.add(DomainIpPO.builder()
                            .ip(t)
                            .detail(ipDetail)
                            .build());
                });
                return DomainIpVO.builder()
                        .po(pos)
                        .totalCount(ips.size())
                        .pageNum(pageNum)
                        .pageSize(pageSize)
                        .build();
            }
            return null;
        }
        List<DomainIpPO> list = dnsDomainIpService.queryPageByCondition(vo,pageNum,pageSize);
        Integer count = 1000000;
        return DomainIpVO.builder()
                .po(list)
                .totalCount(count)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }
    @GetMapping("/getIpRegion")
    @ResponseBody
    public List<IpRegionVO> getIpRegion(String domain){
        List<DomainDependencePO> listDomain = dnsDomainDependenceService.queryAllByCondition(
                DomainDependencePO.builder()
                        .domain(domain)
                        .crossDomain(2)
                        .build());

        if(CollectionUtils.isEmpty(listDomain)) {
            return null;
        }
        String xml = listDomain.get(0).getDomainIp();
        List<String> ips = getAllIp(xml);
        if(CollectionUtils.isEmpty(ips)){
            return null;
        }
        List<DomainIpPO> pos1 = new ArrayList<>();
        //获取ip信息
        ips.forEach(t->{
            List<DomainIpPO> domainIpPOList = dnsDomainIpService.queryAllByCondition(DomainIpPO.builder()
                    .ip(t).build());
            String region = Optional.ofNullable(domainIpPOList).map(t1->t1.get(0).getRegion()).orElse("服务器信息未知");
            pos1.add(DomainIpPO.builder()
                .ip(t)
                .region(region)
                .build());
        });
        List<IpRegionVO> vos = new ArrayList<>();
        pos1.stream().collect(Collectors.groupingBy(DomainIpPO::getRegion)).values().forEach(t->{
            Integer count = t.size();
            String region = t.get(0).getRegion();
            vos.add(IpRegionVO.builder()
                    .value(count)
                    .name(region)
                    .build());
        });
        return vos;
    }
    private List<String> getAllIp(String xml1){
        Document doc;
        if(Objects.equals(xml1,"None")){
            xml1 = "<root>none.com</root>";
        }
        try{
            doc = DocumentHelper.parseText(xml1);
        }catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        // 获取根节点
        Element rootElt = doc.getRootElement();
        List<Element> listElement=rootElt.elements();
        List<String> resList = new ArrayList<>();
        listElement.forEach(t->{
            Attribute nameAttribute = t.attribute("type");
            String ip ="";
            if(nameAttribute != null  && Objects.equals(nameAttribute.getValue(),"A")){
                ip = t.getTextTrim();
            }
            if(StringUtils.isNotBlank(ip)&&!ip.contains(";")){
                resList.add(ip);
            }
        });
        return resList;
    }
}
