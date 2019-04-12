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
public class DomainIpPO {

    public static String ID_COLUMN_NAME="id";
    private Integer id;
    private Integer isVaild;

    /**
     * 域名服务器
     */
    private String domainServer;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 服务器位置
     */
    private String detail;

    private String region;

    private String domain;



}
