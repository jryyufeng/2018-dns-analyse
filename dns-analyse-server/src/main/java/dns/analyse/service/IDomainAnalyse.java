package dns.analyse.service;

import dns.analyse.dao.model.DomainAnalysePO;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/24 下午3:40
 * Description:
 */
public interface IDomainAnalyse extends IDnsBaseService<DomainAnalysePO> {
    DomainAnalysePO queryByDomain(String domain);
}
