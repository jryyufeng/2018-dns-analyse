package dns.analyse.service.impl;

import dns.analyse.dao.model.DomainAnalysePO;
import dns.analyse.dao.model.DomainNetWorkPO;
import dns.analyse.service.IDomainAnalyse;
import dns.analyse.service.IDomainNetworkService;
import dns.analyse.service.IHandleNetworkNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/21
 * @time: 19:47
 */
@Service
public class HandleNetWorkServiceImpl implements IHandleNetworkNode {
    @Autowired
    IDomainAnalyse domainAnalyse;
    @Autowired
    IDomainNetworkService domainNetworkService;

    @Override
    //需要唯一确定一个图

    public List<String> getVertexByPercent(Integer pattern, double percent, String attribute,String chartNmae,String kind2){
        Integer count = domainNetworkService.queryCountByCondition(DomainNetWorkPO.builder().chart(chartNmae).build());
        Integer needNum = new Double(count*percent).intValue();
        if(pattern == 1){
            List<String> resvers = domainNetworkService.getVertexByType(needNum,attribute,chartNmae,kind2);
            return resvers.stream()
                    .map(t->t.replace("_D","").replace("_C",""))
                    .collect(Collectors.toList());
        }else if(pattern ==2){
            List<String> verList = domainNetworkService.getVertexByType(null,attribute,chartNmae,kind2);
            return new Random()
                    .ints(0, verList.size())
                    .limit(needNum)
                    .mapToObj(t->verList.get(t)
                            .replace("_D","")
                            .replace("_C",""))
                    .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    @Override
    public List<String> computeInvalid(List<DomainAnalysePO> pos, List<String> vertexs){
        List<String> mcss = pos.stream().map(DomainAnalysePO::getMcs).collect(Collectors.toList());
        List<List<String>> allMcsList = new ArrayList<>();
        mcss.forEach(t->{
            if(t != null) {
                List<String> mcsList = Arrays.asList(t.replace("(","").split("\\)"));
                mcsList.forEach(k->{
                    allMcsList.add(Arrays.asList(k.split("\\+")));
                });
            }
        });
        Integer allNum = allMcsList.size();
        int a =0;
        for (List<String> mcs:
             allMcsList) {
            if(vertexs.containsAll(mcs)){
                a+=1;
                System.out.println(a);
            }else{
                System.out.println(mcs);
            }
        }
        DecimalFormat df=new DecimalFormat("0.0000");
        double ss= (double)a/(double)allNum;
        String res = df.format(ss*100);
        return Arrays.asList(allNum.toString(),Integer.toString(a),res+"%");
    }
    @Override
    public List<DomainAnalysePO> getMcss(String kind2){
        List<String> ids = Arrays.asList(kind2.split("_"));
        Integer start = Integer.parseInt(ids.get(1))*Integer.parseInt(ids.get(0))-Integer.parseInt(ids.get(0));
        Integer end = Integer.parseInt(ids.get(1))*Integer.parseInt(ids.get(0));
        //Integer end =100;
        return domainAnalyse.queryByStartAndEnd(start,end);
    }


}
