package dns.analyse.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSON;
import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.mapper.DomainDetailDAO;
import dns.analyse.dao.model.DomainAnalysePO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.AbstractJunitTest;
import dns.analyse.dao.model.DomainDetailPO;
import dns.analyse.dao.model.DomainNetDetailPO;
import dns.analyse.service.IDnsDomainCdnService;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IDomainDetailService;
import dns.analyse.service.processors.CreateDomainDetailProcess;
import dns.analyse.service.tools.JedisUtil;

import dns.analyse.service.tools.LogAnnotation;
import jnr.ffi.annotations.In;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.junit.Test;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 18/4/19 下午2:03
 * Description:
 */
public class DomainDependenceDAOTest extends AbstractJunitTest {
    private static final Logger LOGGER = LogManager.getLogger(DomainDependenceDAOTest.class.getName());
    @Autowired
    private DomainDependenceDAO domainDependenceDAO;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private DomainDetailDAO domainDetailDAO;
    @Resource
    private IDomainDetailService domainDetailService;
    @Resource
    private IDnsDomainDependenceService dnsDomainDependenceService;
    @Autowired
    private IDnsDomainCdnService dnsDomainCdnService;
    @Test
    @Rollback(false)  // 避免事务回滚
    public void insert() {
        DomainDependencePO po=new DomainDependencePO();

        po.setDomain("Domain");
        po.setDomainTree("DomainTree");
        po.setDomainIp("DomainIp");
        po.setFlag(1);
        po.setMpsExist(1);
        po.setDomainNum(1);
        po.setIsValid(1);
        Integer ret = domainDependenceDAO.insert(po);
        System.out.println("ret=="+ret);
        ////Assert.assertTrue(ret == 1);
    }

    @Test
    public void queryById() {
        LOGGER.warn("1234");
        List<DomainDependencePO> po=domainDependenceDAO.queryByIds(Arrays.asList(1));
        System.out.println("po=="+ JSON.toJSON(po));
        ////Assert.assertTrue(po != null);
    }

    @Test
    public void queryByProperty(){

        //DomainDependencePO po=domainDependenceDAO.queryByProperty(DomainDependencePO.APP_NAME_COLUMN_NAME,"testvalue");
        //System.out.println("po=="+po);
        //Assert.assertTrue(po != null);
    }

    @Test
    public void queryAllByProperty(){

        //List<DomainDependencePO> poList=domainDependenceDAO.queryAllByProperty(DomainDependencePO.APP_NAME_COLUMN_NAME,"testvalue");
        //System.out.println("poList.size=="+poList.size());
        //Assert.assertTrue(poList.size() != 0);
    }

    @Test
    public void queryInAllByProperty(){

        List l=new ArrayList();
        l.add(1);
        l.add(2);
       // List<DomainDependencePO> poList=domainDependenceDAO.queryInAllByProperty(DomainDependencePO.APP_NAME_COLUMN_NAME,l);
        //System.out.println("poList.size=="+poList.size());
        //System.out.println("poList=="+poList);
        //Assert.assertTrue(poList.size() != 0);
    }


    @Test
    public void queryAllByCondition(){

        DomainDependencePO domainDependencePO=new DomainDependencePO();
        domainDependencePO.setDomain("Domain");
        domainDependencePO.setDomainTree("DomainTree");
        domainDependencePO.setDomainIp("DomainIp");
        domainDependencePO.setFlag(1);
        domainDependencePO.setMpsExist(1);
        domainDependencePO.setDomainNum(1);
        domainDependencePO.setIsValid(1);

        List<DomainDependencePO> poList=domainDependenceDAO.queryAllByCondition(domainDependencePO);
        System.out.println("poList.size=="+poList.size());
        //Assert.assertTrue(poList.size() != 0);
    }


    @Test
    public void queryCountByCondition(){

        DomainDependencePO domainDependencePO=new DomainDependencePO();
//            domainDependencePO.setDomain("Domain");
//            domainDependencePO.setDomainTree("DomainTree");
//            domainDependencePO.setDomainIp("DomainIp");
//            domainDependencePO.setFlag(1);
            domainDependencePO.setMpsExist(1);
//            domainDependencePO.setDomainNum(1);
//            domainDependencePO.setIsValid(1);
        Integer count=domainDependenceDAO.queryCountByCondition(domainDependencePO);
        System.out.println("count=="+count);
    }

    @Test
    public void queryPageByCondition(){

        Integer from=0;
        Integer pageSize=10;

        DomainDependencePO domainDependencePO=new DomainDependencePO();
            domainDependencePO.setDomain("Domain");
        List<DomainDependencePO> poList=domainDependenceDAO.queryPageByCondition(domainDependencePO,from,pageSize);
        System.out.println("poList.size=="+poList.size());
        //Assert.assertTrue(poList.size() != 0);
    }

    @Test
    //@Rollback(false)  // 避免事务回滚
    public void updateByCondition(){
        //domainDependenceDAO.queryAllByCondition(DomainAnalysePO.builder());
        DomainDependencePO domainDependencePO=new DomainDependencePO();
        domainDependencePO.setDomain("Domain");
        domainDependencePO.setDomainTree("DomainTree");
        domainDependencePO.setDomainIp("DomainIp");
        domainDependencePO.setFlag(1);
        domainDependencePO.setMpsExist(1);
        domainDependencePO.setDomainNum(1);
        domainDependencePO.setIsValid(1);

        DomainDependencePO condition=new DomainDependencePO();
        condition.setDomain("Domain");
        condition.setDomainTree("DomainTree");
        condition.setDomainIp("DomainIp");
        condition.setFlag(1);
        condition.setMpsExist(1);
        condition.setDomainNum(1);
        condition.setIsValid(1);
        Integer i=domainDependenceDAO.updateByCondition(domainDependencePO,condition);
        System.out.println("i=="+i);
        ////Assert.assertTrue(res == 1);
    }
    @Test
    public void test1 () {
        // redisCacheManager.set("test12","123890",60);
       display(jedisUtil.demo_set());
    }
    @Test
    @Scheduled(cron = "0/5 * * * * ?")
    public void testLog () {
        display("123");
        LOGGER.warn("123sfsfdsfds");
    }
    @Test
    public void testPyrhon(){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("print('hello')");

    }
    @Test
    @Rollback(false)
    public void testXml(){
        List<DomainDependencePO> po = domainDependenceDAO.queryByIds(Collections.singletonList(3));
        List<DomainNetDetailPO> pos =CreateDomainDetailProcess.getObjectByXml(po.get(0).getDomainTree());
        String json = JSON.toJSONString(pos);
        Integer count = pos.stream().filter(a -> a.getFlag()).collect(Collectors.toList()).size();
        domainDetailDAO.insert(DomainDetailPO.builder()
                .domain(po.get(0).getDomain())
                .domainDetial(json)
                .domainOutNum(count)
                .build());
    }

    @Test
    @Rollback(false)
    public void testDetail(){
        //包含开始，不包含结尾
        //500000，550000补数据
        //for(int i=0;i<91;i++) {
            domainDetailService.detailHandler(950000,1000000);
            System.out.println("\n=========================================\n");
       // }

        //List<DomainDependencePO> po = domainDependenceDAO.queryByIdStartAndEnd(1,10);
    }
    @Test
    @Rollback(false)
    public void testCache(){
        //display(dnsDomainDependenceService.getDOMAIN_NUM(1));
        //for(int i=0;i<91;i++){
            dnsDomainDependenceService.setDomainType(950000,1000000);
            System.out.println("\n-----------------------------------\n");
        //}



    }

    @Test
    public void getTypeNum(){
        List<DomainDependencePO> pos = dnsDomainDependenceService.queryAllByCondition(new DomainDependencePO());

    }

    @Test
    @LogAnnotation
    public void testLog1(){
        System.out.println("111");
    }

    @Test
    public void testCdnCache(){
        display(dnsDomainDependenceService.getDOMAIN_NUMTYPE("=0"));
    }


}
