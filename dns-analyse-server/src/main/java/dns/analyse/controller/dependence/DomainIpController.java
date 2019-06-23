package dns.analyse.controller.dependence;

import com.alibaba.fastjson.JSON;
import dns.analyse.Enum.CountryIfChinaEnum;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainIpPO;
import dns.analyse.model.DomainIpVO;
import dns.analyse.model.IpCountryVO;
import dns.analyse.model.IpRegionVO;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IDnsDomainIpService;
import dns.analyse.service.tools.JudjeCountry;
import dns.analyse.service.tools.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(DomainIpController.class.getName());
    @Autowired
    IDnsDomainIpService dnsDomainIpService;
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;
    @Autowired
    RedisCacheManager redisCacheManager;

    //查询结果入缓存，先在缓存中取，没有再访问数据库

    /**
     * 分页接口，分页获取Ip列表
     * @param vo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public DomainIpVO getList(DomainIpPO vo, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize){
        if(StringUtils.isNotBlank(vo.getDomain())){
            List<DomainIpPO> pos ;
            Object list = redisCacheManager.get(vo.getDomain()+"_ipInfo");
            if(list == null){
                pos = getIpPos(vo.getDomain());
            }else{
                pos = JSON.parseArray(list.toString(), DomainIpPO.class);
            }
            if(CollectionUtils.isEmpty(pos)){
                return null;
            }
            return DomainIpVO.builder()
                    .po(pos)
                    .totalCount(pos.size())
                    .pageNum(pageNum)
                    .pageSize(pos.size())
                    .build();
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

    /**
     * 根据domain获取，该域名下服务器的IP信息
     * @param domain
     * @return
     */
    @GetMapping("/getIpRegion")
    @ResponseBody
    public List<IpRegionVO> getIpRegion(String domain){
        List<DomainIpPO> pos1 ;
        Object list = redisCacheManager.get(domain+"_ipInfo");
        if(list == null){
            pos1 = getIpPos(domain);
        }else{
            pos1 = JSON.parseArray(list.toString(), DomainIpPO.class);
        }
        if(CollectionUtils.isEmpty(pos1)){
            return null;
        }
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
    /**
     * 根据domain获取，该域名下服务器的IP的城市信息
     * @param domain
     * @return
     */
    @GetMapping("/getIpInfo/country")
    @ResponseBody
    public IpCountryVO getIpCountry(String domain){
        Object ipStr = redisCacheManager.get(domain+"_ipInfo");
        List<DomainIpPO> pos2 ;
        if(ipStr != null){
            pos2 = JSON.parseArray(ipStr.toString(),DomainIpPO.class);
        }else {
            pos2 = getIpPos(domain);
        }
        if(CollectionUtils.isEmpty(pos2)){
            return null;
        }
        Map<String,List<DomainIpPO>> mapPos = pos2.stream().collect(Collectors.groupingBy(DomainIpPO::getRegion));
        int in =0,out=0,not=0;
        List<IpCountryVO.ExternalValue> externalValues =new ArrayList<>();
        for (String key : mapPos.keySet()){
            if(Objects.equals(JudjeCountry.judje(key), CountryIfChinaEnum.IN)){
                in+=mapPos.get(key).size();
            }else if(Objects.equals(JudjeCountry.judje(key), CountryIfChinaEnum.OUT)){
                out+=mapPos.get(key).size();
                externalValues.add(IpCountryVO.ExternalValue.builder()
                        .name(key)
                        .value(mapPos.get(key).size())
                        .build());
            }else{
                not+=mapPos.get(key).size();
            }
        }
        return IpCountryVO.builder()
                .external(out)
                .internal(in)
                .not(not)
                .infoList(externalValues)
                .build();
//        mapPos.forEach((k,v)->{
//            if(Objects.equals(JudjeCountry.judje(k), CountryIfChinaEnum.IN)){
//                in+=1;
//            }
//        });
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

    private List<DomainIpPO> getIpPos(String domain){
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
            String ipDetail = Optional.ofNullable(domainIpPOList).map(t1->t1.get(0).getDetail()).orElse("服务器信息未知");
            pos1.add(DomainIpPO.builder()
                    .ip(t)
                    .region(region)
                    .detail(ipDetail)
                    .build());
        });
        //写入redis缓存
        String json = JSON.toJSONString(pos1);
        boolean cacheRes = redisCacheManager.set(domain+"_ipInfo",json,2);
        if(!cacheRes){
            LOGGER.error("ipInfo写入缓存失败,domain:{}",domain);
        }
        return pos1;
    }
}
