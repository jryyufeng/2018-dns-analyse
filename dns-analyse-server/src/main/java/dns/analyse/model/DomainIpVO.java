package dns.analyse.model;

import dns.analyse.dao.model.DomainDependencePO;
import dns.analyse.dao.model.DomainIpPO;
import lombok.*;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/3/11 下午7:35
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainIpVO {
    Integer pageNum;
    Integer pageSize;
    List<DomainIpPO> po;
    Integer totalCount ;
}
