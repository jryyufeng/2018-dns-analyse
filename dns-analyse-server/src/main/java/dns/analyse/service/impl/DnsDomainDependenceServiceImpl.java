package dns.analyse.service.impl;

import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.service.IDnsDomainDependenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/12/23 上午10:51
 * Description:
 */
@Service(value = "dnsDomainDependenceService")
public class DnsDomainDependenceServiceImpl implements IDnsDomainDependenceService {
    @Autowired
    DomainDependenceDAO domainDependenceDAO;

    @Override
    public Integer insert(DomainDependencePO po) {
        return domainDependenceDAO.insert(po);
    }

    @Override
    public Integer update(DomainDependencePO po, DomainDependencePO condition) {
        return domainDependenceDAO.updateByCondition(po, condition);
    }


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    @Override
    public List<DomainDependencePO> queryByIds(List<Integer> list) {
        return domainDependenceDAO.queryByIds(list);

    }


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    @Override
    public List<DomainDependencePO> queryAllByCondition(DomainDependencePO condition) {

        return domainDependenceDAO.queryAllByCondition(condition);

    }

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    @Override
    public Integer queryCountByCondition(DomainDependencePO condition) {
        return domainDependenceDAO.queryCountByCondition(condition);
    }

    /**
     * 根据条件分页查询
     *
     * @return
     */
    @Override
    public List<DomainDependencePO> queryPageByCondition(DomainDependencePO condition, Integer offset, Integer pageSize) {
        return domainDependenceDAO.queryPageByCondition(condition, (offset - 1) * pageSize, pageSize);
    }


}
