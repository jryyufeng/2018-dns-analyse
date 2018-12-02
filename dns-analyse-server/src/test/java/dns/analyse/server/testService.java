package dns.analyse.server;

import dns.analyse.AbstractJunitTest;
import dns.analyse.dao.mapper.SchoolTreeMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/11/22 下午3:26
 * Description:
 */
public class testService extends AbstractJunitTest {
    @Autowired
    SchoolTreeMapper schoolTreeMapper;
    @Test
    public void test1(){
        display(schoolTreeMapper.queryById(1));
    }
}
