package dns.analyse.service;

import dns.analyse.dao.model.DomainCdnPO;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:07
 * Description:
 */
public interface IDnsDomainCdnService {
    Integer insert(DomainCdnPO po);
    Integer update( DomainCdnPO po,DomainCdnPO condition);


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    List< DomainCdnPO> queryByIds(List<Integer> list);


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    List< DomainCdnPO> queryAllByCondition(DomainCdnPO condition);

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    Integer queryCountByCondition(DomainCdnPO condition );

    /**
     * 根据条件分页查询
     *
     * @return
     */
    List<DomainCdnPO> queryPageByCondition(DomainCdnPO condition, Integer offset, Integer pageSize);
    int getCDN_NUM(String cdn);
}
