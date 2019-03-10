package dns.analyse.service.impl;

import com.alibaba.fastjson.JSON;
import dns.analyse.dao.mapper.BaseDAO;
import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.mapper.DomainDetailDAO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainDetailPO;
import dns.analyse.dao.model.DomainNetDetailPO;
import dns.analyse.service.IDomainDetailService;
import dns.analyse.service.impl.DnsServiceImpl;
import dns.analyse.service.processors.CreateDomainDetailProcess;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:31
 * Description:
 */
@Service
public class DomainDetailServiceImpl extends DnsServiceImpl<DomainDetailPO> implements IDomainDetailService {
    @Autowired
    DomainDetailDAO domainDetailDAO;
    @Autowired
    private DomainDependenceDAO domainDependenceDAO;

    @Override
    protected BaseDAO getEntityDao() {
        return domainDetailDAO;
    }

    @Override
    //包含开始，不包含结尾

    public void detailHandler(Integer start,Integer end){
        List<DomainDependencePO> po = domainDependenceDAO.queryByIdStartAndEnd(start,end);
        po.forEach(t->{
            List<DomainNetDetailPO> pos =CreateDomainDetailProcess.getObjectByXml(t.getDomainTree());
            String json = JSON.toJSONString(pos);
            Integer count = pos.stream().filter(a -> a.getFlag()).collect(Collectors.toList()).size();
            domainDetailDAO.insert(DomainDetailPO.builder()
                    .domain(t.getDomain())
                    .domainDetial(json)
                    .domainOutNum(count)
                    .build());
        });

    }

}
