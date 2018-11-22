package dns.analyse.dao.model;

import lombok.*;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2018/10/14 下午6:54
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SchoolTree {
    private int id;
    private int pid;
    private String name;
    private int isParent;
}
