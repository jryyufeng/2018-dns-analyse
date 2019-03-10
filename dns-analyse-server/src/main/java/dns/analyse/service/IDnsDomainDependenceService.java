package dns.analyse.service;

import dns.analyse.dao.model.DomainDependencePO;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/12/23 上午10:28
 * Description:
 */
@Validated
public interface IDnsDomainDependenceService {
    Integer insert(DomainDependencePO po);
    Integer update( DomainDependencePO po,DomainDependencePO condition);


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    List< DomainDependencePO> queryByIds(List<Integer> list);


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    List< DomainDependencePO> queryAllByCondition(DomainDependencePO condition);

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    Integer queryCountByCondition(DomainDependencePO condition );

    /**
     * 根据条件分页查询
     *
     * @return
     */
    List<DomainDependencePO> queryPageByCondition(DomainDependencePO condition, Integer offset, Integer pageSize);

    Integer getDOMAIN_NUM(Integer type);
    Boolean setDomainType(Integer start,Integer end);

    Map<String,Integer>  getDomainProportion();
}
