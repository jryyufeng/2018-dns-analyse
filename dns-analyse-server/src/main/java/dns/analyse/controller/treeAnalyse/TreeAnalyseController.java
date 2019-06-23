package dns.analyse.controller.treeAnalyse;

import com.alibaba.fastjson.JSON;
import dns.analyse.dao.model.DomainAnalysePO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.model.AnalyseServerInfoVO;
import dns.analyse.model.DomainAnalyseVO;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IDomainAnalyse;
import dns.analyse.service.tools.RedisCacheManager;
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

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/24 下午3:34
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/admin/treeAnalyse/api")
public class TreeAnalyseController {
    @Autowired
    IDomainAnalyse domainAnalyse;
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;
    @Autowired
    RedisCacheManager redisCacheManager;

    /**
     * 分页查询接口，返回故障树结果列表
     * @param po
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public DomainAnalyseVO getList(DomainAnalysePO po, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize){
        List<DomainAnalysePO> list = domainAnalyse.queryPageByCondition(po,pageNum,pageSize);
        Integer count = 1000000;
        if(po.getTag() == 1){
            count = 867076;
        }else if(po.getTag() == 0){
            count = 1000000-867076;
        }
        return DomainAnalyseVO.builder()
                .pageSize(pageSize)
                .pageNum(pageNum)
                .totalCount(count)
                .result(list)
                .build();
    }

    /**
     * 根据domain，查询域名下服务器结构重要度列表
     * @param domain
     * @return
     */
    @GetMapping("/queryServerInfo")
    @ResponseBody
    public List<AnalyseServerInfoVO> queryServerInfo(@RequestParam(defaultValue = "www.baidu.com") String domain){

        List<DomainDependencePO> pos1 = dnsDomainDependenceService.queryAllByCondition(DomainDependencePO.builder().domain(domain).crossDomain(2).build());
        if(CollectionUtils.isEmpty(pos1)){
            return null;
        }
        Document doc;
        try{
            doc = DocumentHelper.parseText(pos1.get(0).getDomainIp());
        }catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        // 获取根节点
        Element rootElt = doc.getRootElement();
        List<AnalyseServerInfoVO> transitionStates = new ArrayList<>();
        List<AnalyseServerInfoVO> vos = getNodesNew(transitionStates,rootElt);
        return vos;

    }
    private  List<AnalyseServerInfoVO> getNodesNew(List<AnalyseServerInfoVO> transitionStates,Element node){
        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();
        Attribute nameAttribute = node.attribute("domain");
        if(nameAttribute != null&& org.apache.commons.lang3.StringUtils.isNotBlank(node.getTextTrim())){
            AnalyseServerInfoVO transitionState =new AnalyseServerInfoVO();
            transitionState.setServerName(nameAttribute.getValue());
            transitionState.setIp(node.getTextTrim());
            transitionStates.add(transitionState);
        }
        //遍历所有一级子节点
        for(Element e:listElement){
            //递归
            getNodesNew(transitionStates,e);
        }
        return transitionStates;

    }

    /**
     *根据domain，查询域名下服务器结构重要度列表
     * @param domain
     * @return
     */
    @GetMapping("/queryImportance")
    @ResponseBody
    public List<AnalyseServerInfoVO> queryImportance(@RequestParam(defaultValue = "www.baidu.com") String domain){
        if(StringUtils.isBlank(domain)){
            return null;
        }
        DomainAnalysePO po = getAnalysePO(domain);
        if(po == null){
            return null;
        }
        String importance = po.getStructImportance();
        List<AnalyseServerInfoVO> vos = new ArrayList<>();
        Arrays.asList(importance.split("\\)\\(")).forEach(t->{
            List<String> a = Arrays.asList(t.split(","));
            if(CollectionUtils.isNotEmpty(a) && a.size() ==2){
                vos.add(AnalyseServerInfoVO.builder()
                        .importance(a.get(1).replace(")",""))
                        .serverName(a.get(0).replace("(",""))
                        .build());
            }
        });
        return vos;
    }
    private DomainAnalysePO getAnalysePO(String domain){
        DomainAnalysePO po ;
        boolean res;
        Object poStr = redisCacheManager.get(domain+"_analysePo");
        if(poStr == null){
            po = domainAnalyse.queryByDomain(domain);
            String redisStr = JSON.toJSONString(po);
            try {
                res = redisCacheManager.set(domain+"_analysePo",redisStr,2);

            }catch (Exception e){
                log.error("故障树重要度结果，插入缓存失败:{}",domain,e);
                return null;
            }
            return res ? po:null;
        }
        try {
            po = JSON.parseObject(poStr.toString(),DomainAnalysePO.class);
            return po;
        }catch (Exception e){
            log.error("缓存数据json反序列化失败:{}",domain,e);
            return null;
        }
    }

    /**
     * 查询某域名的割集详情
     * @param domain
     * @return
     */
    @GetMapping("/queryMcs")
    @ResponseBody
    public List<String> queryMcs(@RequestParam(defaultValue = "www.baidu.com") String domain){
        if(StringUtils.isBlank(domain)){
            return null;
        }
        DomainAnalysePO po = getAnalysePO(domain);
        if(Objects.equals(po,null)){
            return null;
        }
        String importance = po.getMcs();
        if(StringUtils.isBlank(importance)){
            return null;
        }
        return Arrays.asList(importance.split("\\)\\("));
    }
    /**
     * 查询某域名的路集详情
     * @param domain
     * @return
     */
    @GetMapping("/queryMps")
    @ResponseBody
    public List<String> queryMps(@RequestParam(defaultValue = "www.baidu.com") String domain){
        if(StringUtils.isBlank(domain)){
            return null;
        }
        DomainAnalysePO po = getAnalysePO(domain);
        if(Objects.equals(po,null)){
            return null;
        }
        String importance = po.getMps();
        if(StringUtils.isBlank(importance)){
            return null;
        }
        return Arrays.asList(importance.split("\\)\\("));
    }
    /**
     * 格式化查询某域名中存在某服务器的割集集合
     * @param domain
     * @return
     */
    @GetMapping("/speculative/failureMps")
    @ResponseBody
    public List<String> failureMps(@RequestParam(defaultValue = "www.baidu.com") String domain,String server){
        if(StringUtils.isBlank(domain) || StringUtils.isBlank(server)){
            return null;
        }
        DomainAnalysePO po = domainAnalyse.queryByDomain(domain);
        List<String> mps = Arrays.asList(po.getMps().split("\\)\\("));
        List<String> resMps = new ArrayList<>();
        mps.forEach(t->{
            if(t.contains(server)){
                resMps.add(t);
            }
        });
        return resMps;
    }
}























