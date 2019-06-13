package dns.analyse.service.impl;

import dns.analyse.Enum.DomainTypeEnum;
import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.mapper.DomainNetWorkDAO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.service.IDnsDomainDependenceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.util.CollectionUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.PostConstruct;
import java.util.Arrays;
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
    private static final Logger LOGGER = LogManager.getLogger(DnsDomainDependenceServiceImpl.class.getName());
    @Autowired
    DomainDependenceDAO domainDependenceDAO;
    @Autowired
    DomainNetWorkDAO domainNetWorkDAO;
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
    @Override
    public Boolean setDomainTypeByDomain(String domain){
        List<DomainDependencePO> pos = domainDependenceDAO.queryAllByCondition(DomainDependencePO.builder().domain(domain).build());
        if(CollectionUtils.isEmpty(pos)){
            return false;
        }
        String[] domains = pos.get(0).getDomain().split("\\.");
        String isCn = domains[domains.length-1];
        if(isCn.contains("/")){
            isCn = isCn.split("/")[0];
        }
        Integer num = domainDependenceDAO.updateByCondition(DomainDependencePO.builder()
                .isCn(isCn)
                .build(),DomainDependencePO.builder()
                .id(pos.get(0).getId())
                .build());
        return num == 1;
    }

    /**
     * 以后要入缓存
     * @return
     */
    @Override
    public Map<String,Integer> getDomainProportion(){


        return null;
    }

    @Override
    public int getDOMAIN_TYPE_NUM(String type){
        try {
            Integer count = DOMAIN_TYPE_NUM_CACHE.get(type);
            return count;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getDOMAIN_NUMTYPE(String numType){
        try {
            Integer count = DOMAIN_NUMTYPE_CACHE.get(numType);
            return count;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int getNetWorkChartNum(String chartName){
        try {
            Integer count = NETWORK_NUM_CACHE.get(chartName);
            return count;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private LoadingCache<String,Integer> DOMAIN_NUMTYPE_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(30,TimeUnit.DAYS)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                    int res =  0;
                    switch (s){
                        case "=0":
                            res = domainDependenceDAO.queryNumByDomainNum(-1,0);
                            break;
                        case "1-3":
                            res = domainDependenceDAO.queryNumByDomainNum(0,3);
                            break;
                        case "3-5":
                            res = domainDependenceDAO.queryNumByDomainNum(3,5);
                            break;
                        case "5-10":
                            res = domainDependenceDAO.queryNumByDomainNum(5,10);
                            break;
                        case "10-15":
                            res = domainDependenceDAO.queryNumByDomainNum(10,15);
                            break;
                            default:
                                res = domainDependenceDAO.queryNumByDomainNum(15,1000);

                    }
                    return res;
                }
            });
    private LoadingCache<String,Integer> DOMAIN_TYPE_NUM_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(30,TimeUnit.DAYS)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                   Integer res =  domainDependenceDAO.queryNumByType(s);
                    return res;
                }
            });
    private LoadingCache<String,Integer> NETWORK_NUM_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(30,TimeUnit.DAYS)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                    Integer res =  domainNetWorkDAO.queryCountByCharts(Arrays.asList(s.split(";")));
                    return res;
                }
            });
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
        //初始化跨域类型缓存
        try {
            DOMAIN_NUM_CACHE.get(DomainTypeEnum.ALL.getCode());
            DOMAIN_NUM_CACHE.get(DomainTypeEnum.IS_CROSS.getCode());
            DOMAIN_NUM_CACHE.get(DomainTypeEnum.NOT_CROSS.getCode());
           // DOMAIN_NUM_CACHE.get(t.getGroupName());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //初始化子域数量类型缓存
        Arrays.asList("=0","1-3","3-5","5-10","10-15",">15").forEach(t->{
            try{
                DOMAIN_NUMTYPE_CACHE.get(t);
            }catch (ExecutionException e){
                LOGGER.error("分类获取不同子域数量域名失败",e);
            }

        });
        //初始化顶级域类型缓存
        Arrays.asList("cn","com","org","jp","other").forEach(t->{
            try {
                DOMAIN_TYPE_NUM_CACHE.get(t);
            }catch (ExecutionException e){
                LOGGER.error("初始化顶级域类型缓存失败",e);
            }
        });

    }


}
