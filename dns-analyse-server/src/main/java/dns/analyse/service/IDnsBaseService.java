package dns.analyse.service;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/1/23 下午8:16
 * Description:
 */
public interface IDnsBaseService<E> {
    Integer insert(E po);
    Integer update( E po,E condition);


    /**
     * 根据属性in查询
     *
     * @param list
     * @return
     */
    List<E> queryByIds(List<Integer> list);


    /**
     * 根据条件查询所有对象
     *
     * @return
     */
    List< E> queryAllByCondition(E condition);

    /**
     * 根据条件查询记录数
     *
     * @return
     */
    Integer queryCountByCondition(E condition );

    /**
     * 根据条件分页查询
     *
     * @return
     */
    List<E> queryPageByCondition(E condition, Integer offset, Integer pageSize);

}
