package dns.analyse.Enum;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/3/2 下午5:23
 * Description:
 */
public enum DomainTypeEnum {
    ALL(2, "all"),
    IS_CROSS(1, "is_cross"),
    NOT_CROSS(0, "not_cross");
    private int code;
    private String show;
    DomainTypeEnum(int code, String show) {
        this.code = code;
        this.show = show;
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.show;
    }

    public static DomainTypeEnum getFromCode(Integer code) {
        for (DomainTypeEnum statusEnum : DomainTypeEnum.values()) {
            if (code == statusEnum.getCode()) {
                return statusEnum;
            }
        }
        return null;
    }
}
