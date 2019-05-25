package dns.analyse.service;

import dns.analyse.dao.model.DomainAnalysePO;

import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/21
 * @time: 19:12
 */
public interface IHandleNetworkNode {
    List<String> getVertexByPercent(Integer pattern,double percent,String attribute,String chartNmae,String kind2);
    List<String> computeInvalid(List<DomainAnalysePO> pos,List<String> vertexs);
    List<DomainAnalysePO> getMcss(String kind2);
}
