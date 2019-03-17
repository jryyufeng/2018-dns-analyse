package dns.analyse.model;

import lombok.*;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/3/16 下午2:32
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AnalyseServerInfoVO {
    private String serverName;
    private String ip;
    private String importance;

}
