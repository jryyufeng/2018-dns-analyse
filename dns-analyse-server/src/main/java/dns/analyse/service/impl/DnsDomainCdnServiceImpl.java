package dns.analyse.service.impl;

import dns.analyse.dao.mapper.DomainCdnDAO;
import dns.analyse.dao.model.DomainCdnPO;
import dns.analyse.service.IDnsDomainCdnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:09
 * Description:
 */
@Service
public class DnsDomainCdnServiceImpl implements IDnsDomainCdnService {
    @Autowired
    DomainCdnDAO domainCdnDAO;

    @Override
    public Integer insert(DomainCdnPO po) {
        return domainCdnDAO.insert(po);
    }

    @Override
    public Integer update(DomainCdnPO po, DomainCdnPO condition) {
        return domainCdnDAO.updateByCondition(po, condition);
    }


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    @Override
    public List<DomainCdnPO> queryByIds(List<Integer> list) {
        return domainCdnDAO.queryByIds(list);

    }


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    @Override
    public List<DomainCdnPO> queryAllByCondition(DomainCdnPO condition) {

        return domainCdnDAO.queryAllByCondition(condition);

    }

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    @Override
    public Integer queryCountByCondition(DomainCdnPO condition) {
        return domainCdnDAO.queryCountByCondition(condition);
    }

    /**
     * 根据条件分页查询
     *
     * @return
     */
    @Override
    public List<DomainCdnPO> queryPageByCondition(DomainCdnPO condition, Integer offset, Integer pageSize) {
        return domainCdnDAO.queryPageByCondition(condition, (offset - 1) * pageSize, pageSize);
    }


}
