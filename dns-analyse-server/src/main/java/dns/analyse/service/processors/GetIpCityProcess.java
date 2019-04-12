package dns.analyse.service.processors;

import dns.analyse.dao.model.DomainIpPO;
import dns.analyse.service.IDnsDomainIpService;
import jnr.ffi.annotations.In;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/4/12
 * @time: 14:49
 */
@Service
public class GetIpCityProcess {
    private static final Logger LOGGER = LogManager.getLogger(GetIpCityProcess.class.getName());
    private static final List<String> CITYLIST = Arrays.asList("北京","上海","天津","重庆","河北","河南","云南","辽宁","黑龙江","湖南"
            ,"安徽","山东","新疆","江苏","浙江","江西","湖北","广西","甘肃","山西","内蒙古","陕西","吉林","福建","贵州","广东","青海","西藏","四川","宁夏","海南","台湾","香港","澳门");

    @Autowired
    IDnsDomainIpService dnsDomainIpService;
    public void handleRegion(Integer start,Integer end){
        List<DomainIpPO> list1 = dnsDomainIpService.queryByStartAndEnd(start,end);

        list1.forEach(t->{
            boolean res = getCity(Optional.ofNullable(t.getDetail()).orElse(""),t.getId());
            if(!res){
                LOGGER.error("分析IP地区信息失败,:{}",t.getId());
                return;
            }
        });
    }
    private boolean getCity(String detail,Integer id){
        Integer res = 0;
        for (String city:
             CITYLIST) {
            if(detail.contains(city)){
                res = dnsDomainIpService.update(DomainIpPO.builder().region(city).build(),DomainIpPO.builder().id(id).build());
                break;
            }
        }
        if(res == 0){
            res = dnsDomainIpService.update(DomainIpPO.builder().region(detail.split(" ")[0]).build(),DomainIpPO.builder().id(id).build());
        }
        System.out.println(id);
        return res == 1;
    }
}
