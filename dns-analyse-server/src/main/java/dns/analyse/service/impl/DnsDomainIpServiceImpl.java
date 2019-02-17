package dns.analyse.service.impl;

import dns.analyse.dao.mapper.BaseDAO;
import dns.analyse.dao.mapper.DomainIpDAO;
import dns.analyse.dao.model.DomainIpPO;
import dns.analyse.service.IDnsDomainIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:24
 * Description:
 */
@Service
public class DnsDomainIpServiceImpl extends DnsServiceImpl<DomainIpPO> implements IDnsDomainIpService {
    @Autowired
    private DomainIpDAO domainIpDAO;

    @Override
    protected BaseDAO getEntityDao() {
        return domainIpDAO;
    }
}
