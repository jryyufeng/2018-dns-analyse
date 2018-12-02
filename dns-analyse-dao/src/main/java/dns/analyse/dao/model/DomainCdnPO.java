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
public class DomainCdnPO {

    public static String ID_COLUMN_NAME="id";
    public static String IS_VALID_COLUMN_NAME="is_valid";
    private Long id;
    private Integer isValid;

    /**
     * 域名
     */
    private String domain;
    /**
     * cdn服务器
     */
    private String cdnServer;
    /**
     * 服务器ip
     */
    private String cdnIp;


}
