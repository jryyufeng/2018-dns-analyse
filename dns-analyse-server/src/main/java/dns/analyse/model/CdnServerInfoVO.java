package dns.analyse.model;

import lombok.*;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/3/10 下午4:52
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CdnServerInfoVO {
    private String server;
    private String serverIp;
    private String serverDetail;
}
