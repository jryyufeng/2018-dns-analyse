package dns.analyse.dao.model;

import lombok.*;

import java.util.List;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/28 下午3:41
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainNetDetailPO {
    private String subnetName;
    private List<String> serverList;
    private Boolean flag = false;
    private List<String> outServerList;

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter
//    @Setter
//    @ToString
//    @Builder
//    public static class ServerOut {
//        private String serverOutName; //被依赖的那个域，不太好获取
//        private List<String> serverList;
//    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class transitionState{
        private String domain;
        private String serverList;
    }
}
