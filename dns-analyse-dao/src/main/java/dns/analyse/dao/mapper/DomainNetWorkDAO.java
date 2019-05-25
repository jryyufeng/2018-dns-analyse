package dns.analyse.dao.mapper;

import dns.analyse.dao.model.DomainNetWorkPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/12
 * @time: 18:55
 */
public interface DomainNetWorkDAO extends BaseDAO<DomainNetWorkPO,Integer> {
    List<String> getVertexByType(@Param("needNum")Integer needNum,@Param("type")String type,@Param("chart") String chart,@Param("kind2") String kind2);
}
