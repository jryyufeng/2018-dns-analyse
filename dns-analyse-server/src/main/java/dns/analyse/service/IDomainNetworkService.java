package dns.analyse.service;

import dns.analyse.dao.model.DomainNetWorkPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/17
 * @time: 11:00
 */
public interface IDomainNetworkService extends IDnsBaseService<DomainNetWorkPO>{
    Integer get_CHARTINFO(String chart);
    List<String> getVertexByType(Integer needNum,String type,String chart,String kind2);
}
