package dns.analyse.model;

import dns.analyse.dao.model.DomainAnalysePO;
import lombok.*;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/24 下午3:46
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainAnalyseVO {
    Integer pageNum;
    Integer pageSize;
    List<DomainAnalysePO> result;
    Integer totalCount ;
}
