package dns.analyse.model;

import dns.analyse.dao.model.DomainNetWorkPO;
import lombok.*;

import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/14
 * @time: 17:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainNetworkVO {
    Integer pageNum;
    Integer pageSize;
    List<DomainNetWorkPO> pos;
    Integer totalCount ;
}
