package dns.analyse.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import  java.lang.Long;

import com.meituan.bep.sqt.lib.common.util.JsonUtil;
import dns.analyse.dao.mapper.DomainDependenceDAO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.AbstractJunitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.annotation.Rollback;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 18/4/19 下午2:03
 * Description:
 */
public class DomainDependenceDAOTest extends AbstractJunitTest {
    @Autowired
    private DomainDependenceDAO domainDependenceDAO;
      


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

        Long ret = domainDependenceDAO.insert(po);
        System.out.println("ret=="+ret);
        ////Assert.assertTrue(ret == 1);
    }

    @Test
    public void queryById() {

        DomainDependencePO po=domainDependenceDAO.queryById(Long.valueOf(1));
        System.out.println("po=="+po);
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
    public void queryAll(){

        List<DomainDependencePO> poList=domainDependenceDAO.queryAll();
        System.out.println("poList.size=="+poList.size());
        //Assert.assertTrue(poList.size() != 0);
    }


    @Test
    public void queryCountByCondition(){

        DomainDependencePO domainDependencePO=new DomainDependencePO();
            domainDependencePO.setDomain("Domain");
            domainDependencePO.setDomainTree("DomainTree");
            domainDependencePO.setDomainIp("DomainIp");
            domainDependencePO.setFlag(1);
            domainDependencePO.setMpsExist(1);
            domainDependencePO.setDomainNum(1);
            domainDependencePO.setIsValid(1);
        Integer count=domainDependenceDAO.queryCountByCondition(domainDependencePO);
        System.out.println("count=="+count);
    }

    @Test
    public void queryPageByCondition(){

        Integer from=0;
        Integer pageSize=10;

        DomainDependencePO domainDependencePO=new DomainDependencePO();
            domainDependencePO.setDomain("Domain");
            domainDependencePO.setDomainTree("DomainTree");
            domainDependencePO.setDomainIp("DomainIp");
            domainDependencePO.setFlag(1);
            domainDependencePO.setMpsExist(1);
            domainDependencePO.setDomainNum(1);
            domainDependencePO.setIsValid(1);

        List<DomainDependencePO> poList=domainDependenceDAO.queryPageByCondition(domainDependencePO,from,pageSize);
        System.out.println("poList.size=="+poList.size());
        //Assert.assertTrue(poList.size() != 0);
    }
    @Test
    public void deleteById(){

        Integer i=domainDependenceDAO.deleteById(Long.valueOf(1),"gu");
        System.out.println("i=="+i);
        ////Assert.assertTrue(res == 1);
    }

    @Test
    public void deleteAllByCondition(){


        DomainDependencePO domainDependencePO=new DomainDependencePO();
            domainDependencePO.setDomain("Domain");
            domainDependencePO.setDomainTree("DomainTree");
            domainDependencePO.setDomainIp("DomainIp");
            domainDependencePO.setFlag(1);
            domainDependencePO.setMpsExist(1);
            domainDependencePO.setDomainNum(1);
            domainDependencePO.setIsValid(1);
        Integer i=domainDependenceDAO.deleteAllByCondition(domainDependencePO,"gu");
        System.out.println("i=="+i);
        ////Assert.assertTrue(res == 1);
    }


    @Test
    //@Rollback(false)  // 避免事务回滚
    public void updateById(){

        DomainDependencePO domainDependencePO=new DomainDependencePO();
            domainDependencePO.setDomain("Domain");
            domainDependencePO.setDomainTree("DomainTree");
            domainDependencePO.setDomainIp("DomainIp");
            domainDependencePO.setFlag(1);
            domainDependencePO.setMpsExist(1);
            domainDependencePO.setDomainNum(1);
            domainDependencePO.setIsValid(1);
        Integer i=domainDependenceDAO.updateById(domainDependencePO,Long.valueOf(1));
        System.out.println("i=="+i);
        ////Assert.assertTrue(res == 1);
    }

    @Test
    //@Rollback(false)  // 避免事务回滚
    public void updateByCondition(){

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

}
