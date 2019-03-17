package dns.analyse.service.impl;

import dns.analyse.dao.mapper.BaseDAO;
import dns.analyse.dao.mapper.DomainAnalyseDAO;
import dns.analyse.dao.model.DomainAnalysePO;
import dns.analyse.service.IDomainAnalyse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/24 下午3:41
 * Description:
 */
@Service
public class DomainAnalyseServiceImpl extends DnsServiceImpl<DomainAnalysePO> implements IDomainAnalyse {
    @Autowired
    DomainAnalyseDAO domainAnalyseDAO;

    @Override
    protected BaseDAO getEntityDao() {
        return domainAnalyseDAO;
    }

    @Override
    public DomainAnalysePO queryByDomain(String domain){
        return domainAnalyseDAO.queryByDomain(domain);
    }
}
