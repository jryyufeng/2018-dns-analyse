package dns.analyse.dao.mapper;

import dns.analyse.dao.model.SchoolTree;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/10/14 下午6:56
 * Description:
 */
public interface SchoolTreeMapper {
    List<SchoolTree> queryById(Integer pid);
}
