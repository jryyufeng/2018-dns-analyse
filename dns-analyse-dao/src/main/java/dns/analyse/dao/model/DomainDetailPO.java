package dns.analyse.dao.model;

import javax.print.DocFlavor.STRING;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 18/5/7 下午3:02
 * Description:
 * * from maker
 */
public class DomainDetailPO {

    public static String ID_COLUMN_NAME="id";
    public static String IS_VALID_COLUMN_NAME="is_valid";
    private Long id;
    private Integer isValid;
    /**
     * 域名
     */
    private String domain;
    /**
     * 域外依赖个数
     */
    private Integer domainOutNum;
    /**
     * 域依赖详情
     */
    private String domainDetial;

    /**
     * 域名ip子网详情
     */
    private String subnetDetial;

    public void setDomain(String value) {
        this.domain = value;
        }
    public String getDomain() {
        return this.domain;
        }
    public void setDomainOutNum(Integer value) {
        this.domainOutNum = value;
        }
    public Integer getDomainOutNum() {
        return this.domainOutNum;
        }
    public void setDomainDetial(String value) {
        this.domainDetial = value;
        }
    public String getDomainDetial() {
        return this.domainDetial;
        }

    public void setSubnetDetial(String value) {
        this.subnetDetial = value;
        }
    public String getSubnetDetial() {
        return this.subnetDetial;
        }

}
