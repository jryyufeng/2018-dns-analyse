package dns.analyse.dao.model;

import lombok.*;


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
public class DomainDependencePO {
    private Long id;           // 主键id
    private Integer isValid;   //是否删除  1: 有效 0: 删除
    /**
     * domain
     */
    private String domain;
    /**
     * domainTree
     */
    private String domainTree;
    /**
     * domainIp
     */
    private String domainIp;
    /**
     * flag
     */
    private Integer flag;

    /**
     * mpsExist
     */
    private Integer mpsExist;
    /**
     * domainNum
     */
    private Integer domainNum;

    private Integer crossDomain;

}
