package dns.analyse;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public abstract class AbstractJunitTest extends AbstractTransactionalJUnit4SpringContextTests {
    //protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractJunitTest.class);

    @Autowired
    @Qualifier("dataSource")
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    private static int i = 1;

    public void display(Object object) {
        this.display(object,true);
    }
    /**
     * Des: 显示log用
     * Method: display
     * User: liujinxin
     * Date: 2017/11/10
     * Time: 上午11:04
     *
     * @param object 要输出内容
     */
    public void display(Object object, boolean displayNull) {

        System.out.println();
        System.out.println("+--------------------------------------------------------|" + i);
        System.out.println();
        System.out.println(JSON.toJSON(object));
        System.out.println();
        System.out.println("+--------------------------------------------------------|" + i++);
        System.out.println();

    }
}
