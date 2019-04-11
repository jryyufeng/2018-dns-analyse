package dns.analyse.dao.mapper;

import java.util.List;
import java.lang.Long;


import dns.analyse.dao.model.DomainCdnPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/5/07 下午12:07
 * Description:
 * from maker
 */
@Repository
public interface DomainCdnDAO extends  BaseDAO<DomainCdnPO,Integer>  {

    int queryConutByDisTinct (@Param("item") String item);

}
