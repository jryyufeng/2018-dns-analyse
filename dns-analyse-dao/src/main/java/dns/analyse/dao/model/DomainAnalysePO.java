package dns.analyse.dao.model;

import lombok.*;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/14 下午5:12
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainAnalysePO {

    private Integer id;

    private String mcs;

    private String mps;

    private Integer mcsCount;

    private Integer mpsCount;

    private String domain;

    private Integer tag;

    private String structImportance;

}
