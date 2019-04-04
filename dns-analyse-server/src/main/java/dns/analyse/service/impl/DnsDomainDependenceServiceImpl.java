package dns.analyse.service.impl;

import dns.analyse.Enum.DomainTypeEnum;
import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.service.IDnsDomainDependenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/12/23 上午10:51
 * Description:
 */
@Service(value = "dnsDomainDependenceService")
public class DnsDomainDependenceServiceImpl implements IDnsDomainDependenceService {
    @Autowired
    DomainDependenceDAO domainDependenceDAO;

    @Override
    public Integer insert(DomainDependencePO po) {
        return domainDependenceDAO.insert(po);
    }

    @Override
    public Integer update(DomainDependencePO po, DomainDependencePO condition) {
        return domainDependenceDAO.updateByCondition(po, condition);
    }


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    @Override
    public List<DomainDependencePO> queryByIds(List<Integer> list) {
        return domainDependenceDAO.queryByIds(list);

    }


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    @Override
    public List<DomainDependencePO> queryAllByCondition(DomainDependencePO condition) {

        return domainDependenceDAO.queryAllByCondition(condition);

    }

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    @Override
    public Integer queryCountByCondition(DomainDependencePO condition) {
        return domainDependenceDAO.queryCountByCondition(condition);
    }

    /**
     * 根据条件分页查询
     *
     * @return
     */
    @Override
    public List<DomainDependencePO> queryPageByCondition(DomainDependencePO condition, Integer offset, Integer pageSize) {
        return domainDependenceDAO.queryPageByCondition(condition, (offset - 1) * pageSize, pageSize);
    }
    @Override
    public Integer getDOMAIN_NUM(Integer type){
        try {
            Integer count = DOMAIN_NUM_CACHE.get(type);
            return count;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean setDomainType(Integer start,Integer end){
        List<DomainDependencePO> pos = domainDependenceDAO.queryByIdStartAndEnd(start,end);
        pos.forEach(t->{
            String[] domains = t.getDomain().split("\\.");
            String isCn = domains[domains.length-1];
            if(isCn.contains("/")){
                isCn = isCn.split("/")[0];
            }
            //System.out.println(isCn);

            domainDependenceDAO.updateByCondition(DomainDependencePO.builder()
                    .isCn(isCn)
                    .build(),DomainDependencePO.builder()
                    .id(t.getId())
                    .build());
            System.out.println(t.getDomain());

        });
        return true;
    }

    /**
     * 以后要入缓存
     * @return
     */
    @Override
    public Map<String,Integer> getDomainProportion(){


        return null;
    }



    /**
     * key:id
     * value:Integer
     */
    private LoadingCache<Integer, Integer> DOMAIN_NUM_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build(new CacheLoader<Integer, Integer>() {
                       @Override
                       public Integer load(Integer type) {
                           Integer count =0;
                           DomainDependencePO po = new DomainDependencePO();
                           if(Objects.equals(type,DomainTypeEnum.IS_CROSS.getCode())){
                               //测试先用msp_exist
                               po.setMpsExist(1);
                               //po.setCrossDomain(1);

                           }else if(Objects.equals(type,DomainTypeEnum.NOT_CROSS.getCode())){
                               po.setMpsExist(0);
                               //po.setCrossDomain(0);
                           }
                           count = domainDependenceDAO.queryCountByCondition(po);
                           return count;
                       }
                   }
            );


    @PostConstruct
    public void initCache() {

        try {
            DOMAIN_NUM_CACHE.get(DomainTypeEnum.ALL.getCode());
            DOMAIN_NUM_CACHE.get(DomainTypeEnum.IS_CROSS.getCode());
            DOMAIN_NUM_CACHE.get(DomainTypeEnum.NOT_CROSS.getCode());
           // DOMAIN_NUM_CACHE.get(t.getGroupName());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}
