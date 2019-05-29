package dns.analyse.dao.model;

import lombok.*;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/12
 * @time: 18:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DomainNetWorkPO {

    private Integer id;

    private String vertex;

    private String kind2;

    private Integer type;

    private String chart;

    private Integer inDegree;

    private Double clustering;

    private Double betweenCentrality;

    private Integer outDegree;

    private Integer coreNum;

    private Double eigenvctCentrality;

    private Double pagerank;


}
