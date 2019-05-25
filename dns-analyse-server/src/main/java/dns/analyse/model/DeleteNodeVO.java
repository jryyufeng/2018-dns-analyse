package dns.analyse.model;

import lombok.*;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/22
 * @time: 19:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DeleteNodeVO {
    private String allNum;
    private String deleteNum;
    private String proportion;
    private String message;
}
