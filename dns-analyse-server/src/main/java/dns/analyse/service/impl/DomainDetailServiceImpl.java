package dns.analyse.service.impl;

import dns.analyse.dao.mapper.BaseDAO;
import dns.analyse.dao.mapper.DomainDetailDAO;
import dns.analyse.dao.model.DomainDetailPO;
import dns.analyse.service.IDomainDetailService;
import dns.analyse.service.impl.DnsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:31
 * Description:
 */
public class DomainDetailServiceImpl extends DnsServiceImpl<DomainDetailPO> implements IDomainDetailService {
    @Autowired
    DomainDetailDAO domainDetailDAO;

    @Override
    protected BaseDAO getEntityDao() {
        return domainDetailDAO;
    }

}
