package dns.analyse.controller.treeAnalyse;

import dns.analyse.dao.model.DomainAnalysePO;
import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.model.DomainAnalyseVO;
import dns.analyse.service.IDomainAnalyse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/24 下午3:34
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/admin/treeAnalyse/api")
public class TreeAnalyseController {
    @Autowired
    IDomainAnalyse domainAnalyse;


    @GetMapping("/list")
    @ResponseBody
    public DomainAnalyseVO getList(DomainAnalysePO po, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize){
        List<DomainAnalysePO> list = domainAnalyse.queryPageByCondition(po,pageNum,pageSize);
        Integer count = 1000000;
        if(po.getTag() == 1){
            count = 867076;
        }else if(po.getTag() == 0){
            count = 1000000-867076;
        }
        return DomainAnalyseVO.builder()
                .pageSize(pageSize)
                .pageNum(pageNum)
                .totalCount(count)
                .result(list)
                .build();
    }
}
