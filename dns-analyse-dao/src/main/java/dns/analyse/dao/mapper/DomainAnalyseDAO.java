package dns.analyse.dao.mapper;

import dns.analyse.dao.model.DomainAnalysePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/14 下午5:12
 * Description:
 */
@Repository
public interface DomainAnalyseDAO extends BaseDAO<DomainAnalyseDAO,Integer> {
    DomainAnalysePO queryByDomain(@Param("domain") String domain);
}
