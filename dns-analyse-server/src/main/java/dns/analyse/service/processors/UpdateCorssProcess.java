package dns.analyse.service.processors;

import com.alibaba.fastjson.JSON;
import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.mapper.DomainDetailDAO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainDetailPO;
import dns.analyse.dao.model.DomainNetDetailPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/4/26
 * @time: 15:20
 */
@Service
public class UpdateCorssProcess {
    @Resource
    DomainDetailDAO domainDetailDAO;
    @Resource
    DomainDependenceDAO domainDependenceDAO;
    public boolean updateCorss(Integer start,Integer end){
        List<DomainDetailPO> pos = domainDetailDAO.queryByIdStartAndEnd(start,end);
        pos.forEach(t->{
            String doamin = t.getDomain();
            List<DomainNetDetailPO> netDetailPOS = JSON.parseArray(t.getDomainDetial(),DomainNetDetailPO.class);
           boolean flag =  netDetailPOS.stream().anyMatch(DomainNetDetailPO::getFlag);
           if(flag){
               Integer num = domainDependenceDAO.updateByCondition(DomainDependencePO.builder().crossDomain(1).build(),
                       DomainDependencePO.builder().domain(doamin).build()
               );
               System.out.println(doamin+"-------"+num);
           }
        });
        return true;
    }
}
