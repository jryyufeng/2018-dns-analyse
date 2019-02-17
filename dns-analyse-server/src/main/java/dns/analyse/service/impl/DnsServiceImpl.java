package dns.analyse.service.impl;

import dns.analyse.dao.mapper.BaseDAO;
import dns.analyse.service.IDnsBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:18
 * Description:
 */
@Service
public abstract class DnsServiceImpl<E> implements IDnsBaseService<E> {

    protected  abstract BaseDAO getEntityDao();

    @Override
    public Integer insert(E po) {
        return getEntityDao().insert(po);
    }

    @Override
    public Integer update(E po, E condition) {
        return getEntityDao().updateByCondition(po, condition);
    }


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    @Override
    public List<E> queryByIds(List<Integer> list) {
        return getEntityDao().queryByIds(list);

    }


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    @Override
    public List<E> queryAllByCondition(E condition) {

        return getEntityDao().queryAllByCondition(condition);

    }

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    @Override
    public Integer queryCountByCondition(E condition) {
        return getEntityDao().queryCountByCondition(condition);
    }

    /**
     * 根据条件分页查询
     *
     * @return
     */
    @Override
    public List<E> queryPageByCondition(E condition, Integer offset, Integer pageSize) {
        return getEntityDao().queryPageByCondition(condition, (offset - 1) * pageSize, pageSize);
    }

}
