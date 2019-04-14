package dns.analyse.service.tools;

import dns.analyse.Enum.CountryIfChinaEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/4/13
 * @time: 17:57
 */
public class JudjeCountry {
    private static final List<String> CITYLIST = Arrays.asList("北京","上海","天津","重庆","河北","河南","云南","辽宁","黑龙江","湖南"
            ,"安徽","山东","新疆","江苏","浙江","江西","湖北","广西","甘肃","山西","内蒙古","陕西","吉林","福建","贵州","广东","青海","西藏","四川","宁夏","海南","台湾","香港","澳门");

    //国内true 国外false
    public static CountryIfChinaEnum judje(String regoin){
        if(CITYLIST.contains(regoin)){
            return CountryIfChinaEnum.IN;
        }else if(Objects.equals(regoin,"服务器信息未知")|| Objects.equals(regoin,"未知国家")){
            return CountryIfChinaEnum.NOT;
        }else {
            return CountryIfChinaEnum.OUT;
        }
    }
}
