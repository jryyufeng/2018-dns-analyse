
package dns.analyse.dao.mapper;

import java.io.Serializable;
import java.util.List;
import java.lang.Long;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/5/07 下午12:07
 * Description:
 */

public interface BaseDAO<E,PK> {

    Integer insert(E po);

    E queryById(PK id);
    List<E> queryByIds(List<PK> id);

    /**
     * 根据条件查询所有对象
     * @return
     */
    List<E> queryAllByCondition(@Param("condition") E condition);


    /**
     * 根据条件查询记录数
     * @return
     */
    int queryCountByCondition(@Param("condition") E condition);

    /**
     * 根据条件分页查询
     * @return
     */
    List<E> queryPageByCondition(@Param("condition") E condition, @Param("fromNum") Integer fromNum, @Param("pageSize") Integer pageSize);

    /**
     * 根据条件更新所有对象
     * @return
     */
    Integer updateByCondition(@Param("po") E po, @Param("condition") E condition);




}
