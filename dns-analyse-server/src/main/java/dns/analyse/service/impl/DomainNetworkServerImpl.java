package dns.analyse.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dns.analyse.dao.mapper.BaseDAO;
import dns.analyse.dao.mapper.DomainNetWorkDAO;
import dns.analyse.dao.model.DomainNetWorkPO;
import dns.analyse.service.IDomainNetworkService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/17
 * @time: 11:10
 */
@Service
public class DomainNetworkServerImpl extends DnsServiceImpl<DomainNetWorkPO> implements IDomainNetworkService {
    @Autowired
    DomainNetWorkDAO domainNetWorkDAO;

    @Override
    protected BaseDAO getEntityDao() {
        return domainNetWorkDAO;
    }

    @Override
    public List<String> getVertexByType(Integer needNum,String type,String chart,String kind2){
       return domainNetWorkDAO.getVertexByType(needNum,type,chart,kind2);
    }
    @Override
    public Integer get_CHARTINFO(String chart){
        try {
            Integer count = CHARTINFO.get(chart);
            return count;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private LoadingCache<String,Integer> CHARTINFO = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String chartName) throws Exception {
                    Integer total = domainNetWorkDAO.queryCountByCondition(DomainNetWorkPO.builder().chart(chartName).build());
                    return total;
                }
            });

    @PostConstruct
    public void initCache() {
        try {
            CHARTINFO.get("graph_chain_100");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
