package dns.analyse.Enum;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/4/13
 * @time: 18:00
 */
public enum CountryIfChinaEnum {
    IN(1, "in"),
    OUT(2, "out"),
    NOT(0, "not");
    private int code;
    private String show;
    CountryIfChinaEnum(int code, String show) {
        this.code = code;
        this.show = show;
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.show;
    }

    public static CountryIfChinaEnum getFromCode(Integer code) {
        for (CountryIfChinaEnum statusEnum : CountryIfChinaEnum.values()) {
            if (code == statusEnum.getCode()) {
                return statusEnum;
            }
        }
        return null;
    }
}
