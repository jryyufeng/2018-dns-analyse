package dns.analyse.model;

import lombok.*;

import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/4/13
 * @time: 11:23
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class IpCountryVO {
    private Integer internal;
    private Integer external;
    private Integer not;
    private List<ExternalValue> infoList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class ExternalValue{
        private String name;
        private Integer value = 0;
    }
}
