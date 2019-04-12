package dns.analyse.service;


import dns.analyse.dao.model.DomainIpPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:13
 * Description:
 */
public interface IDnsDomainIpService extends IDnsBaseService<DomainIpPO> {
    List<DomainIpPO> queryByStartAndEnd(Integer start, Integer end);
}
