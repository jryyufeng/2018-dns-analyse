package dns.analyse.service.impl;

import dns.analyse.dao.mapper.DomainCdnDAO;
import dns.analyse.dao.model.DomainCdnPO;
import dns.analyse.service.IDnsDomainCdnService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:09
 * Description:
 */
@Service
public class DnsDomainCdnServiceImpl implements IDnsDomainCdnService {
    @Autowired
    DomainCdnDAO domainCdnDAO;

    @Override
    public Integer insert(DomainCdnPO po) {
        return domainCdnDAO.insert(po);
    }

    @Override
    public Integer update(DomainCdnPO po, DomainCdnPO condition) {
        return domainCdnDAO.updateByCondition(po, condition);
    }


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    @Override
    public List<DomainCdnPO> queryByIds(List<Integer> list) {
        return domainCdnDAO.queryByIds(list);

    }


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    @Override
    public List<DomainCdnPO> queryAllByCondition(DomainCdnPO condition) {

        return domainCdnDAO.queryAllByCondition(condition);

    }

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    @Override
    public Integer queryCountByCondition(DomainCdnPO condition) {
        return domainCdnDAO.queryCountByCondition(condition);
    }

    /**
     * 根据条件分页查询
     *
     * @return
     */
    @Override
    public List<DomainCdnPO> queryPageByCondition(DomainCdnPO condition, Integer offset, Integer pageSize) {
        return domainCdnDAO.queryPageByCondition(condition, (offset - 1) * pageSize, pageSize);
    }

    @Override
    public int getCDN_NUM(String cdn){
        try {
            Integer count = CDN_NUM_CACHE.get(cdn);
            return count;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private LoadingCache<String, Integer> CDN_NUM_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build(new CacheLoader<String, Integer>() {
                       @Override
                       public Integer load(String cdn) {
                           Integer sa = domainCdnDAO.queryConutByDisTinct(cdn);
                           return sa;
                       }
                   }
            );



}
