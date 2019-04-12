package dns.analyse.model;

import lombok.*;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/4/12
 * @time: 21:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class IpRegionVO {
    private String name;
    private Integer value = 0;
}
