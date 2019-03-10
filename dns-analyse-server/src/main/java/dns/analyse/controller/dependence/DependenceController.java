package dns.analyse.controller.dependence;

import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.model.DomainDependenceVO;
import dns.analyse.service.IDnsDomainDependenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/23 下午4:07
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/admin/dependence/api")
public class DependenceController {
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;

    @GetMapping("/list")
    @ResponseBody
    public DomainDependenceVO getList(DomainDependencePO vo,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize){
        List<DomainDependencePO> list = dnsDomainDependenceService.queryPageByCondition(vo,pageNum,pageSize);
        Integer count = 1000000;
        return DomainDependenceVO.builder()
                .po(list)
                .totalCount(count)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }
//    @GetMapping("/cdnList")
//    @ResponseBody
//    public

}
