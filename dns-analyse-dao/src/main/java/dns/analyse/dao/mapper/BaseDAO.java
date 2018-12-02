
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

    Long insert(E po);

    E queryById(PK id);

    /**
     * 根据属性查询对象
     * @param property
     * @param value
     * @return
     */
    E queryByProperty(@Param("property") String property, @Param("value") String value);

    /**
     * 根据属性查询所有对象
     * @param property
     * @param value
     * @return
     */
    List<E> queryAllByProperty(@Param("property") String property, @Param("value") String value);

    /**
     * 根据属性in查询
     * @param property
     * @param list
     * @return
     */
    List<E> queryInAllByProperty(@Param("property") String property, @Param("list") List list);


    /**
     * 根据条件查询所有对象
     * @return
     */
    List<E> queryAllByCondition(@Param("condition") E condition);

    /**
     * 查询所有对象
     * @return
     */
    List<E> queryAll();

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



    Integer deleteById(@Param("id") PK id, @Param("deleter") String deleter);

    /**
     * 根据条件删除所有对象
     * @return
     */
    Integer deleteAllByCondition(@Param("condition") E condition, @Param("deleter") String deleter);



    Integer updateById(@Param("po") E po, @Param("id") PK id);

    /**
     * 根据条件更新所有对象
     * @return
     */
    Integer updateByCondition(@Param("po") E po, @Param("condition") E condition);




}
