package dns.analyse.model;

import dns.analyse.dao.model.DomainDependencePO;
import lombok.*;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/23 下午4:10
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainDependenceVO {
    Integer pageNum;
    Integer pageSize;
    List<DomainDependencePO> po;
    Integer totalCount ;
}
