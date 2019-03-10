package dns.analyse.dao.model;

import lombok.*;

import javax.print.DocFlavor.STRING;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 18/5/7 下午3:02
 * Description:
 * * from maker
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainDetailPO {
    private Long id;
    private Integer isValid;
    /**
     * 域名
     */
    private String domain;
    /**
     * 域外依赖个数
     */
    private Integer domainOutNum = 0;
    /**
     * 域依赖详情
     */
    private String domainDetial;

    /**
     * 域名ip子网详情
     */
    private String subnetDetial;

}
